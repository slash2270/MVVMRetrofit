package com.example.mvvmretrofit

import android.content.Context
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rx.Observable
import java.util.*

class DataModel {

    interface APIService {
        @GET("photos/")
        fun obData(): Observable<List<MainBean>>

        @GET("photos/") // uri
        fun callData(): Call<List<MainBean>>
    }

    fun getData(
        context: Context,
        binding: ActivityMainBinding,
        list: ArrayList<MainBean>
    ) {

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/") // url
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val apiService: APIService = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            apiService.callData().enqueue(object : Callback<List<MainBean>> {
                // 屬性必須為Call
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

    }

    fun adapter(list: ArrayList<MainBean>, context: Context, binding: ActivityMainBinding) {

        val rvAdapter = RvAdapter(list, context)
        binding.recyclerView.adapter = rvAdapter
        rvAdapter.notifyDataSetChanged()

    }

}