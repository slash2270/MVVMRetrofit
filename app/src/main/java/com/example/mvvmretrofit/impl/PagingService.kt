package com.example.mvvmretrofit.impl

import com.example.mvvmretrofit.Constants.Companion.REPO_LIST
import com.example.mvvmretrofit.data.DataRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface PagingService {
    @GET(REPO_LIST)
    suspend fun getRepositories(@Query("page") page: Int, @Query("per_page") perPage: Int): DataRepository
}