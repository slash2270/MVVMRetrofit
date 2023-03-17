package com.example.mvvmretrofit.model

import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import com.example.mvvmretrofit.activity.MainActivity

class DynamicItemViewModel(var mBaseActivity: MainActivity, var type: Int, text: String) {
    @JvmField
    val text = ObservableField<String?>()
    @JvmField
    var click = View.OnClickListener {
        Toast.makeText(
            mBaseActivity,
            "你點擊了 $text",
            Toast.LENGTH_SHORT
        ).show()
    }

    init {
        this.text.set(text)
    }
}