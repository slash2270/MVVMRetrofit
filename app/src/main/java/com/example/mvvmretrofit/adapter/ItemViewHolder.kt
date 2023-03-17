package com.example.mvvmretrofit.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.BR

class ItemViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    fun bindItem(obj: Any?) {
        viewDataBinding.setVariable(BR.item, obj)
        viewDataBinding.executePendingBindings()
    }
}