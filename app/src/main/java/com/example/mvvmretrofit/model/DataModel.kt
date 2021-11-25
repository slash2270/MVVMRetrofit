package com.example.mvvmretrofit.model

import android.content.Context
import com.example.mvvmretrofit.impl.API
import com.example.mvvmretrofit.bean.ColorBean
import com.example.mvvmretrofit.adapter.RvAdapter
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.StringBuilder
import kotlin.collections.ArrayList

class DataModel {

    interface Title {
        fun getText(text: String?)
    }

    fun titleBar(title: Title){

        val strBuilder = StringBuilder()
        listOf("Id", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "Title", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "\t", "Content")
            .forEach {
                strBuilder.append(it)
            }
        title.getText(strBuilder.toString())

    }

    fun dynamicData(context: Context, binding: ActivityMainBinding, arrayList: ArrayList<ColorBean>?) {

        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val  retrofit = Retrofit.Builder()
             //與 RxJava 1 和 RxJava 2 適配器不同，RxJava 3 適配器的 create() 方法默認會產生異步 HTTP 請求。對於同步請求使用 createSynchronous() 和在調度器上同步使用 createWithScheduler(..)
            // Or .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://jsonplaceholder.typicode.com/") // url
            .build()

        val apiService = retrofit.create(API::class.java)

        // 連續連線
        val observable1 = apiService.observableData1()
        val observable2 = apiService.observableData2()

        observable1.subscribeOn(Schedulers.io()) // （觀察者）切換到IO線程進行網絡請求1
            .observeOn(AndroidSchedulers.mainThread()) // （新觀察者）切換到主線程處理網絡請求1的結果
            .doOnNext { response ->
                response(response, arrayList)
            }
            .observeOn(Schedulers.io())
            // （新被觀察者，同時也是新觀察者）切換到IO線程去發起登錄請求
            // 特別注意：因為平面地圖是對最終被觀察者作變換，所以舊被觀察者，它是新觀察者，所以通過觀察切換線程
            // 但對於最初的觀察者，那才是新的被觀察者
            .flatMap {
                // 作變換，即作編碼網絡請求2
                observable2
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()) // (初始觀察者）切換到主線程處理網絡請求2的結果
            .subscribe({ response -> // 對第2次網絡請求返回的結果進行操作
                response(response, arrayList)
                adapter(context, binding, arrayList)
                //DbManager.addColors(arrayList)
                //println("取得list " + arrayList?.size)
            }, { println("網路連線失敗") })

        //DbManager.addColors(listOf(ColorBean("1","a","t")))

        /*merge(observable1, observable2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{
            val list3 = ArrayList(it)
            adapter(list3, context, binding) //資料合併2
            println("result : ${it.size}")
        }*/

        /*
            CoroutineScope(Dispatchers.IO).launch {

                apiService.callData().enqueue(object : Callback<List<MainBean>> {

                    override fun onResponse(
                        call: Call<List<MainBean>>,
                        response: Response<List<MainBean>>
                    ) {
                        val data = response.body()
                        if (response.isSuccessful && data != null) {
                            data.forEach {
                                list.add(MainBean(it.id, it.title, it.thumbnailUrl))
                            }
                            adapter(list, context, binding)
                        }
                        //Log.d("取得1 ", arrView.size.toString())

                    }

                    override fun onFailure(call: Call<List<MainBean>>, throwable: Throwable) {
                        //Log.d("取得2 " , throwable.toString().trim { it <= ' ' })
                    }
                })

            }
        */

    }

    private fun response(response: List<ColorBean>, list: ArrayList<ColorBean>?){

        response.isNotEmpty().apply {
            //Log.d("取得Response ", response.size.toString())
            for(i in 0 until response.size - 4995){
                list?.add(ColorBean(response[i].id, response[i].title, response[i].thumbnailUrl))
            }
        }

    }

    private fun adapter(context: Context, binding: ActivityMainBinding, arrayList: ArrayList<ColorBean>?) {

        val rvAdapter = RvAdapter(arrayList, context)
        binding.recyclerView.adapter = rvAdapter

    }

}