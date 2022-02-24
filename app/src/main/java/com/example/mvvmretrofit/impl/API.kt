package com.example.mvvmretrofit.impl

import com.example.mvvmretrofit.bean.Color
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface API { // uri

    @GET("photos/")
    fun observableData1(): Observable<List<Color>>

    @GET("photos/")
    fun observableData2(): Observable<List<Color>>

    /*  Retrofit 把網絡請求的 URL 檢測了兩部分：一個改造 Retrofit 對象,另一部分改造網絡請求接口
        如果接口裡的 url 是一個完整的網址，那麼重新安裝對象裡的 URL 可以忽略
        採用Observable<...>接口 */

    @GET("photos/")
    fun callData(): Call<List<Color>>

}