package com.example.mvvmretrofit.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.DbManager
import com.example.mvvmretrofit.model.MainViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DbManager.createDb(applicationContext)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel(binding)
        viewModel.recycler(this)
        viewModel.data(applicationContext)
        viewModel.liveData.observe(this, {
            if (binding.recyclerView.adapter != null) {
                binding.recyclerView.adapter!!.notifyDataSetChanged()
            }
        })
        binding.model = viewModel

    }

}