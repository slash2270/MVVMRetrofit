package com.example.mvvmretrofit

import android.content.Context
import android.util.Log
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.google.gson.Gson
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

    fun dynamicData(context: Context, binding: ActivityMainBinding, arrayList: ArrayList<MainBean>?) {

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val  retrofit = Retrofit.Builder()
             //與 RxJava 1 和 RxJava 2 適配器不同，RxJava 3 適配器的 create() 方法默認會產生異步 HTTP 請求。對於同步請求使用 createSynchronous() 和在調度器上同步使用 createWithScheduler(..)
            // Or .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://jsonplaceholder.typicode.com/") // url
            .build()

        val apiService = retrofit.create(APIService::class.java)

        // 連續連線
        val observable1 = apiService.observableData1()
        val observable2 = apiService.observableData2()

        observable1.subscribeOn(Schedulers.io()) // （初始被观察者）切换到IO线程进行网络请求1
            .observeOn(AndroidSchedulers.mainThread()) // （新观察者）切换到主线程 处理网络请求1的结果
            .doOnNext { response ->
                response(response, arrayList)
            }
            .observeOn(Schedulers.io())
            // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
            // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
            // 但对于初始观察者，它则是新的被观察者
            .flatMap {
                // 作变换，即作嵌套网络请求2
                observable2
            }
            .observeOn(AndroidSchedulers.mainThread()) // （初始观察者）切换到主线程 处理网络请求2的结果
            .subscribe({ response -> // 对第2次网络请求返回的结果进行操作
                response(response, arrayList)
                adapter(context, binding, arrayList)
            }, { println("網路連線失敗") })

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

    private fun response(response: List<MainBean>, list: ArrayList<MainBean>?){

        response.isNotEmpty().apply {
            //Log.d("取得Response ", response.size.toString())
            for(i in 0 until response.size - 4995){
                list?.add(MainBean(response[i].id, response[i].title, response[i].thumbnailUrl))
            }
        }

    }

    private fun adapter(context: Context, binding: ActivityMainBinding, arrayList: ArrayList<MainBean>?) {

        val rvAdapter = RvAdapter(arrayList, context)
        binding.recyclerView.adapter = rvAdapter

    }

}