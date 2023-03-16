package com.example.mvvmretrofit.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.databinding.ActivityWebBinding
import com.example.mvvmretrofit.model.MainViewModel

class WebActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        binding.model = MainViewModel()
        init()
        setting(webView)
        if (Build.VERSION.SDK_INT >= 24) { // 7.0
            val webClient = WebClient(this, progressBar)
            webView.webViewClient = webClient
        }
        intent.getStringExtra("url")?.let { webView.loadUrl(it) }
    }

    private fun init() {
        webView = binding.webView
        progressBar = binding.progressBar
    }

    private fun setting(view: WebView){
        val setting = view.settings
//        setting.setAppCacheEnabled(true)
        setting.databaseEnabled = true
        setting.domStorageEnabled = true //开启DOM缓存，关闭的话H5自身的一些操作是无效的
        setting.builtInZoomControls = false // 设置支持缩放
        setting.domStorageEnabled = true
        setting.databaseEnabled = true
        setting.javaScriptCanOpenWindowsAutomatically = true // 支持通过Javascript打开新窗口
        setting.javaScriptEnabled = true // 设置jS
        setting.allowFileAccess = true // 设置可以访问文件
        setting.cacheMode = WebSettings.LOAD_NO_CACHE // 新頁面
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("--------onRestart", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e("--------onRestart", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("--------onRestart", "onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.e("--------onRestart", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.stopLoading()
        webView.clearHistory()
        webView.clearCache(true)
        webView.loadUrl("about:blank")
        webView.pauseTimers()
        webView.destroy()
        Log.e("--------onRestart", "onRestart")
    }

}

/*class WebChrome(private val ivClose: ImageView?) : WebChromeClient() { // < 7.0

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if(newProgress == 100){
            view?.visibility = View.VISIBLE
            ivClose?.visibility = View.VISIBLE
        }
        super.onProgressChanged(view, newProgress)
    }

    /*override fun onReceivedTitle(view: WebView, title: String) {
        if (title.contains("404") || title.contains("500") || title.contains("Error")) {
            view.visibility = View.GONE
            ivClose?.visibility = View.GONE
        }
        super.onReceivedTitle(view, title)
    }*/

}*/

class WebClient(private val activity: WebActivity, private val progress: View) : WebViewClient() { // >= 7.0

    private var statusCode = 0
    private var errorCode = 0

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        //Log.i("取得超連結 ", request?.url.toString())
        val hrefUrl = request?.url.toString()
        val hit = view?.hitTestResult
        val hitType = hit?.type
        if (hitType == WebView.HitTestResult.SRC_ANCHOR_TYPE || hitType == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) { // 點擊超鏈接
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(hrefUrl)
            activity.startActivity(i)
        } else {
            view?.loadUrl(hrefUrl)
        }

        return true
        //return super.shouldOverrideUrlLoading(view, request)

    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        handler?.proceed()
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        progress.visibility = View.VISIBLE
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        progress.visibility = View.GONE
        super.onPageFinished(view, url)
    }

    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {

        statusCode = errorResponse?.statusCode!!
        //Log.d("取得LocationDetailStatus = ", "$statusCode")

        if (statusCode == 404 || statusCode == 500 || statusCode <= 0) { // 這個方法在6.0才出現
            fail(view)
        }

        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            errorCode = error?.errorCode!!
        }
        //Log.d("取得LocationDetailError = ", "$errorCode")

        if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT) { // 斷網或者網絡連接超時
            fail(view)
        }

        super.onReceivedError(view, request, error)
    }

    private fun fail(view: WebView?) {
        view?.visibility = View.GONE
    }

}