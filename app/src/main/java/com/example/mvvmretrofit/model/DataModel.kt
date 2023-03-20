package com.example.mvvmretrofit.model

import android.util.Log
import com.example.mvvmretrofit.Constants.Companion.PLACEHOLDER_URL
import com.example.mvvmretrofit.impl.PlaceHolderService
import com.example.mvvmretrofit.bean.ColorBean
import com.example.mvvmretrofit.util.DataStoreUtils
import com.google.gson.GsonBuilder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class DataModel {
    interface Dynamic {
        fun getList(arrayList: ArrayList<ColorBean>)
    }
    fun getTitle() {
        val dataStoreUtils = DataStoreUtils()
        dataStoreUtils.putString("id", "Id")
        dataStoreUtils.putString("title", "Title")
        dataStoreUtils.putString("content", "Content")
        dataStoreUtils.putString("color", "Color")
    }

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()

    private val retrofit = Retrofit.Builder()
        //與 RxJava 1 和 RxJava 2 適配器不同，RxJava 3 適配器的 create() 方法默認會產生異步 HTTP 請求。對於同步請求使用 createSynchronous() 和在調度器上同步使用 createWithScheduler(..)
        // Or .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(PLACEHOLDER_URL) // url
        .build()

    private val apiService = retrofit.create(PlaceHolderService::class.java)

    fun getList(dynamic: Dynamic) {

        val arrayList = ArrayList<ColorBean>()

        // 連續連線
        val observable1 = apiService.observableData1()
        val observable2 = apiService.observableData2()

        observable1
            .subscribeOn(Schedulers.io()) // （觀察者）切換到IO線程進行網絡請求1
            .doOnError { error -> System.err.println("取得 error IO1 message is: " + error.message) }
            .observeOn(AndroidSchedulers.mainThread()) // （新觀察者）切換到主線程處理網絡請求1的結果
            /*.doOnNext { response ->
                arrayList.addAll(ArrayList(response.filter { it.id.toInt() < 6 }))
                Log.d("取得 result1 ", "${arrayList.size}")
            }*/
            .observeOn(Schedulers.io())
            // （新被觀察者，同時也是新觀察者）切換到IO線程去發起登錄請求
            // 特別注意：因為平面地圖是對最終被觀察者作變換，所以舊被觀察者，它是新觀察者，所以通過觀察切換線程
            // 但對於最初的觀察者，那才是新的被觀察者
            .flatMap {
                // 作變換，即作編碼網絡請求2
                observable2
            }
            .subscribeOn(Schedulers.io())
            .doOnError { error -> System.err.println("取得 error IO2 message is: " + error.message) }
            .observeOn(AndroidSchedulers.mainThread()) // (初始觀察者）切換到主線程處理網絡請求2的結果
            /*.subscribe({ response -> // 對第2次網絡請求返回的結果進行操作
                arrayList.addAll(ArrayList(response.filter { it.id.toInt() in 6..10  }))
                dynamic.getList(arrayList)
                Log.d("取得 result2 ", "${arrayList.size}")
            }, { Log.e("取得 Api Error ","網路連線失敗") })*/

        Observable.merge(observable1, observable2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{ list->
            if(arrayList.isEmpty()){
                arrayList.addAll(ArrayList(list.filter { it.id < 6}))
            }else{
                arrayList.addAll(ArrayList(list.filter { it.id in 6..10 }))
            }
            dynamic.getList(arrayList)
            Log.d("取得result size ", "${arrayList.size}")
        }

    }

    fun getDataList(): ArrayList<ColorBean> {

        val arrayList = ArrayList<ColorBean>()

        CoroutineScope(Dispatchers.IO).launch {
            apiService.callData().enqueue(object : retrofit2.Callback<List<ColorBean>> {
                override fun onResponse(call: retrofit2.Call<List<ColorBean>>, response: retrofit2.Response<List<ColorBean>>) {
                    val data = response.body()
                    if (response.isSuccessful && data != null) {
                        arrayList.addAll(ArrayList(data.filter { it.id in 10 ..19 }))
                    }
                    Log.d("取得PageList:", arrayList.size.toString())

                }
                override fun onFailure(call: retrofit2.Call<List<ColorBean>>, throwable: Throwable) {
                    //Log.d("取得2 " , throwable.toString().trim { it <= ' ' })
                }
            })
        }

        return arrayList
    }

}