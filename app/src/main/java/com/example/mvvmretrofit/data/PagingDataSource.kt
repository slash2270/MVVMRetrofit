package com.example.mvvmretrofit.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmretrofit.bean.GithubSearchBean
import network.PagingApiManager

/**
 * 数据源
 */
class PagingDataSource: PagingSource<Int, GithubSearchBean>() {

    override fun getRefreshKey(state: PagingState<Int, GithubSearchBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubSearchBean> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val rspRepository = PagingApiManager.serviceApi.getRepositories(page, pageSize)
            val items = rspRepository.items
            val preKey = if (page > 1) page - 1 else null
            val nextKey = if (items.isNotEmpty()) page + 1 else null
            LoadResult.Page(items, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}