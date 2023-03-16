package com.example.mvvmretrofit.model

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.bean.ColorBean
import androidx.lifecycle.MutableLiveData
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.adapter.FooterAdapter
import com.example.mvvmretrofit.adapter.PagingAdapter
import com.example.mvvmretrofit.adapter.RvAdapter
import com.example.mvvmretrofit.bean.GithubSearchBean
import com.example.mvvmretrofit.data.Repository
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.ColorRepository
import com.example.mvvmretrofit.util.DataStoreFactory.beanDataStore
import com.example.mvvmretrofit.util.DataStoreUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class MainViewModel: ViewModel() {

    val listData: MutableLiveData<ArrayList<ColorBean>> = MutableLiveData<ArrayList<ColorBean>>()
    val ovf = ObservableField("")

    fun recycler(context: Context, binding: ActivityMainBinding) {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewPage.layoutManager = LinearLayoutManager(context)
    }

    fun data(activity: MainActivity, binding: ActivityMainBinding, colorRepository: ColorRepository) {

        val dataModel = DataModel()
        dataModel.getTitle()
        ovf.set(DataStoreUtils.getString("title", "Id\t\t\t\t\t\t\t\t\t\t\t\tTitle\t\t\t\t\t\t\t\t\t\t\t\t\t\tContent"))
        viewModelScope.launch(Dispatchers.IO){
            DataStoreUtils.updateDataStore(activity)
            val data = activity.beanDataStore.data.first()
            val text = (data.id + "\t\t\t\t\t\t\t\t\t\t\t\t" + data.title + "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + data.thumbnailUrl)
            ovf.set(text)
            DataStoreUtils.removeData("title", activity.beanDataStore.data)
        }
        dataModel.getList(object : DataModel.Dynamic{
            override fun getList(arrayList: ArrayList<ColorBean>) {
                listData.value = arrayList
                adapter(activity, binding)
                viewModelScope.launch(Dispatchers.IO) {
                    arrayList.forEach {
                        colorRepository.addColors(it)
                    }
                }
                Log.d("取得 DB List ", "${listData.value?.size}")
            }
        })
        listData.observe(activity) { listColorBean ->
            if (listColorBean.size > 0) {
                binding.recyclerView.adapter?.notifyDataSetChanged()
                /*launch(Dispatchers.Main) {
                    workThread(listColorBean)
                }
                dbManager.closeDb()*/
            }
        }

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
        colorRepository.closeDb()

    }

    private fun adapter(context: Context, binding: ActivityMainBinding) {
        val rvAdapter = RvAdapter(listData.value, context)
        binding.recyclerView.adapter = rvAdapter
    }

    private fun getPagingData(): Flow<PagingData<GithubSearchBean>> {
        return Repository.getPagingData().cachedIn(viewModelScope)
    }

}