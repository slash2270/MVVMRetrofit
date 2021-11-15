package com.example.mvvmretrofit

import android.app.Activity
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.databinding.ActivityMainBinding

class MainViewModel: ViewModel() {

    val ovf = ObservableField("")

    init {

    }

    fun recycler(activity: Activity, binding: ActivityMainBinding){

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    fun data(context: Context, binding: ActivityMainBinding){

        val dataModel = DataModel()
        dataModel.dynamicData(context, binding, ArrayList())
        dataModel.titleBar(object : DataModel.Title{
            override fun getText(text: String?) {
                ovf.set(text)
            }
        })

    }

}