package com.example.mvvmretrofit.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.mvvmretrofit.bean.ColorBean
import androidx.lifecycle.MutableLiveData
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.adapter.PlaceHolderAdapter
import com.example.mvvmretrofit.databinding.RecyclerPlaceholderBinding
import com.example.mvvmretrofit.db.ColorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class PlaceHolderViewModel: ViewModel() {

    val listData: MutableLiveData<ArrayList<ColorBean>> = MutableLiveData<ArrayList<ColorBean>>()

    fun data(activity: MainActivity, binding: RecyclerPlaceholderBinding, colorRepository: ColorRepository) {

        DataModel().getList(object : DataModel.Dynamic{
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
                binding.recyclerViewPlaceHolder.adapter?.notifyDataSetChanged()
                /*launch(Dispatchers.Main) {
                    workThread(listColorBean)
                }
                dbManager.closeDb()*/
            }
        }

    }

    private fun adapter(context: Context, binding: RecyclerPlaceholderBinding) {
        val placeHolderAdapter = PlaceHolderAdapter(listData.value, context)
        binding.recyclerViewPlaceHolder.adapter = placeHolderAdapter
    }

}