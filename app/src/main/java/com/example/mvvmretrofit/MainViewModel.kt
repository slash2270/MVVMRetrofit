package com.example.mvvmretrofit

import android.app.Activity
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.databinding.ActivityMainBinding

class MainViewModel(private val binding: ActivityMainBinding): ViewModel() {

    val ovf = ObservableField("")
    var liveData = MutableLiveData<ArrayList<MainBean>>()

    init {

        initLiveData()

    }

    private fun initLiveData() {

        liveData.value = ArrayList()

    }

    fun recycler(activity: Activity){

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    fun data(context: Context){

        val dataModel = DataModel()
        dataModel.dynamicData(context, binding, liveData.value)
        dataModel.titleBar(object : DataModel.Title{
            override fun getText(text: String?) {
                ovf.set(text)
            }
        })

    }

}