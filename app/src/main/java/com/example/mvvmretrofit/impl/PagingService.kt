package com.example.mvvmretrofit.impl

import com.example.mvvmretrofit.data.DataRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingService {

    companion object{
        const val BASE_URL = "https://api.github.com/"
        const val REPO_LIST = "search/repositories?sort=stars&q=Android"
    }

    @GET(REPO_LIST)
    suspend fun getRepositories(@Query("page") page: Int, @Query("per_page") perPage: Int): DataRepository
}