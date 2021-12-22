package com.example.mvvmretrofit.model

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.bean.ColorBean
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.adapter.RvAdapter
import androidx.lifecycle.MutableLiveData
import com.example.mvvmretrofit.db.DbManager

class MainViewModel(private val binding: ActivityMainBinding): ViewModel() {

    var ovf = ObservableField("")
    var listData = MutableLiveData<ArrayList<ColorBean>>()

    init {

        listData.value = ArrayList()

    }

    fun recycler(activity: Activity) {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    fun data(context: Context, dbManager: DbManager) {

        val dataModel = DataModel()
        dataModel.getTitle(object : DataModel.Text {
            override fun getText(text: String?) {
                ovf.set(text)
            }
        })
        dataModel.getList(object : DataModel.Dynamic{
            override fun getList(arrayList: ArrayList<ColorBean>) {
                listData.value = arrayList
                Log.d("取得 List ", "${listData.value?.size}")
                listData.value?.isNotEmpty().apply {
                    adapter(context)
                }
            }
        }, dbManager)

        Log.d("取得 DB Colors Size ", "${dbManager.getColors()?.size}")

    }

    private fun adapter(context: Context) {

        val rvAdapter = RvAdapter(listData.value, context)
        binding.recyclerView.adapter = rvAdapter

    }

}