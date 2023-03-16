package com.example.mvvmretrofit.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mvvmretrofit.bean.GithubSearchBean
import kotlinx.coroutines.flow.Flow

object Repository {

    private const val PAGE_SIZE = 20

    fun getPagingData(): Flow<PagingData<GithubSearchBean>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {PagingDataSource()}
        ).flow
    }
}