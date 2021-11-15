package com.example.mvvmretrofit
import android.app.Activity
import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmretrofit.databinding.ActivityMainBinding

class MainViewModel: ViewModel() {

    var ovf = ObservableField("")

    init {

        ovf.set("Title")

    }

    fun recycler(activity: Activity, binding: ActivityMainBinding){

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    fun data(context: Context, binding: ActivityMainBinding){

        val dataModel = DataModel()
        dataModel.getData(context, binding, ArrayList())

    }

}