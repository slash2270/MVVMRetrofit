package com.example.mvvmretrofit.activity

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.CoilUtils
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.adapter.RvAdapter
import com.example.mvvmretrofit.databinding.ActivityMainBinding
import com.example.mvvmretrofit.db.ColorRepository
import com.example.mvvmretrofit.model.MainViewModel
import com.example.mvvmretrofit.util.DataStoreFactory
import com.example.mvvmretrofit.util.DataStoreUtils
import com.example.mvvmretrofit.util.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(){

    private lateinit var colorRepository: ColorRepository
    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataStoreFactory.init(applicationContext)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = MainViewModel()
        setCoil()
        colorRepository = ColorRepository()
        colorRepository.createDb(applicationContext)
        Utils.recycler(this, binding.recyclerView, true, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = RvAdapter(this, colorRepository)
        binding.model = viewModel
        colorRepository.closeDb()
    }

    /*private suspend fun workThread(arrayList: ArrayList<ColorBean>) = withContext(Dispatchers.IO) {
        arrayList.forEach {
            dbManager.addColors(it)
        }
    }*/

    private fun setCoil() { // Global
        val okHttpClient = OkHttpClient.Builder()
            .cache(CoilUtils.createDefaultCache(this))
            .build()

        val imageLoader = ImageLoader.Builder(this)
            .availableMemoryPercentage(0.2)
            .diskCachePolicy(CachePolicy.ENABLED)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .crossfade(true)
            .crossfade(3000)
            .okHttpClient { okHttpClient }
            .build()

        Coil.setImageLoader(imageLoader)
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("--------onRestart","onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("--------onStart","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("--------onResume","onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e("--------onStop","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        DataStoreUtils().clearData()
        colorRepository.deleteColorTable()
        Log.e("--------onDestroy","onDestroy")
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