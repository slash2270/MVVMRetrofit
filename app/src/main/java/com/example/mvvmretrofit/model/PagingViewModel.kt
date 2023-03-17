package com.example.mvvmretrofit.model

import android.view.View
import androidx.lifecycle.*
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.adapter.FooterAdapter
import com.example.mvvmretrofit.adapter.PagingAdapter
import com.example.mvvmretrofit.bean.GithubSearchBean
import com.example.mvvmretrofit.data.Repository
import com.example.mvvmretrofit.databinding.RecyclerPageBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PagingViewModel: ViewModel() {

    fun data(activity: MainActivity, binding: RecyclerPageBinding) {

        val pagingAdapter = PagingAdapter(activity)
        binding.recyclerViewPage.adapter = pagingAdapter.withLoadStateFooter(FooterAdapter { pagingAdapter.retry() })
        viewModelScope.launch {
            getPagingData().collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        pagingAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.recyclerViewPage.visibility = View.VISIBLE
                    binding.refresh.isRefreshing = false
                }
                is LoadState.Loading -> {
                    binding.refresh.isRefreshing = true
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerViewPage.visibility = View.INVISIBLE
                }
                is LoadState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.refresh.isRefreshing = false
                }
            }
        }
        binding.refresh.setOnRefreshListener {
            binding.recyclerViewPage.swapAdapter(pagingAdapter,true)
            pagingAdapter.refresh()
        }

    }

    private fun getPagingData(): Flow<PagingData<GithubSearchBean>> {
        return Repository.getPagingData().cachedIn(viewModelScope)
    }

}