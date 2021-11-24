package com.example.mvvmretrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmretrofit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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