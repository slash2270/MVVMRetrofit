package com.example.mvvmretrofit.impl

import com.example.mvvmretrofit.Constants.Companion.PLACEHOLDER_URI
import com.example.mvvmretrofit.bean.ColorBean
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.GET

interface PlaceHolderService { // uri

    @GET(PLACEHOLDER_URI)
    fun observableData1(): Observable<List<ColorBean>>

    @GET(PLACEHOLDER_URI)
    fun observableData2(): Observable<List<ColorBean>>

    /*  Retrofit 把網絡請求的 URL 檢測了兩部分：一個改造 Retrofit 對象,另一部分改造網絡請求接口
        如果接口裡的 url 是一個完整的網址，那麼重新安裝對象裡的 URL 可以忽略
        採用Observable<...>接口 */

    @GET(PLACEHOLDER_URI)
    fun callData(): Call<List<ColorBean>>

}