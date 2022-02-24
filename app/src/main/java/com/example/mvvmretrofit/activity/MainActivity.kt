package com.example.mvvmretrofit.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.ColorRepository
import com.example.mvvmretrofit.model.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(){

    private lateinit var colorRepository: ColorRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorRepository = ColorRepository()
        colorRepository.createDb(applicationContext)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainViewModel()
        viewModel.recycler(this, binding)
        viewModel.data(applicationContext, binding, colorRepository)
        viewModel.listData.observe(this) { listColorBean ->
            if (listColorBean.size > 0) {
                binding.recyclerView.adapter?.notifyDataSetChanged()
                /*launch(Dispatchers.Main) {
                    workThread(listColorBean)
                }
                dbManager.closeDb()*/
            }
        }
        binding.model = viewModel

    }

    /*private suspend fun workThread(arrayList: ArrayList<ColorBean>) = withContext(Dispatchers.IO) {
        arrayList.forEach {
            dbManager.addColors(it)
        }
    }*/

    override fun onRestart() {
        super.onRestart()
        Log.e("--------onRestart","onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("--------onRestart","onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("--------onRestart","onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.e("--------onRestart","onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        colorRepository.deleteColorTable()
        Log.e("--------onRestart","onRestart")
    }

}