package com.example.mvvmretrofit.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.DbManager
import com.example.mvvmretrofit.model.MainViewModel

class MainActivity : AppCompatActivity() {

    private val dbManager = DbManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbManager.createDb(applicationContext)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel(binding)
        viewModel.recycler(this)
        viewModel.data(applicationContext, dbManager)
        viewModel.listData.observe(this){
            if (binding.recyclerView.adapter != null) {
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        binding.model = viewModel

    }

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
        dbManager.deleteColorTable()
        Log.e("--------onRestart","onRestart")
    }

}