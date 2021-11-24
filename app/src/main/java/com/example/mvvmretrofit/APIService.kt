package com.example.mvvmretrofit

import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface APIService { // uri

    @GET("photos/")
    fun observableData1(): Observable<List<MainBean>>

    @GET("photos/")
    fun observableData2(): Observable<List<MainBean>>

    /* 注解里传入 网络请求 的部分URL地址
       Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
       如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
       采用Observable<...>接口 */

    @GET("photos/")
    fun callData(): Call<List<MainBean>>

}