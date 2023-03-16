package network

import com.example.mvvmretrofit.impl.PagingService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory

object PagingApiManager {
    val serviceApi: PagingService by lazy {
        val retrofit = retrofit2.Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .baseUrl(PagingService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PagingService::class.java)
    }
}