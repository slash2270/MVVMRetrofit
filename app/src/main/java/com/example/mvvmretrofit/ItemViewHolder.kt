package com.example.mvvmretrofit

import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.databinding.MainListItemBinding

class ItemViewHolder(var mainListItemBinding: MainListItemBinding) : RecyclerView.ViewHolder(mainListItemBinding.root) {
    fun bindItem(obj: Any?) {
        mainListItemBinding.setVariable(BR.item, obj)
        mainListItemBinding.executePendingBindings()
    }
}