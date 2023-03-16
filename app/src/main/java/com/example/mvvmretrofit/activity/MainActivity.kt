package com.example.mvvmretrofit.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.ColorRepository
import com.example.mvvmretrofit.model.MainViewModel
import com.example.mvvmretrofit.util.DataStoreFactory
import com.example.mvvmretrofit.util.DataStoreUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(){

    private lateinit var colorRepository: ColorRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataStoreFactory.init(applicationContext)
        colorRepository = ColorRepository()
        colorRepository.createDb(applicationContext)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainViewModel()
        viewModel.recycler(this, binding)
        viewModel.data(this, binding, colorRepository)
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
        DataStoreUtils.clearData()
        colorRepository.deleteColorTable()
        Log.e("--------onRestart","onRestart")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出App", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
        }
        return true
    }

}