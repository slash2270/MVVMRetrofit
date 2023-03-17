package com.example.mvvmretrofit.model

import android.text.TextUtils
import android.view.View
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import com.example.mvvmretrofit.activity.MainActivity

class DynamicUiViewModel(val activity: MainActivity) {
    private val dynamicViewModel: DynamicViewModel = DynamicViewModel(activity)

    @JvmField
    var position = OnTextChanged { s, start, before, count ->
        if (!TextUtils.isEmpty(s.toString())) dynamicViewModel.positionStr = s.toString()
    }
    @JvmField
    var add = View.OnClickListener { dynamicViewModel.add() }
    @JvmField
    var delete = View.OnClickListener { dynamicViewModel.delete() }
    @JvmField
    var change = View.OnClickListener { dynamicViewModel.change() }

}