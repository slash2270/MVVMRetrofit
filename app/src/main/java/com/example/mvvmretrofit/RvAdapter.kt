package com.example.mvvmretrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.databinding.MainListItemBinding
import kotlin.collections.ArrayList

class RvAdapter(private val list: ArrayList<MainBean>?, private val context: Context) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: MainListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.main_list_item, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val addBindViewHolder = AddBindViewHolder()
        addBindViewHolder.setBindViewHolder(holder, list, position)
    }

    inner class AddBindViewHolder {

        fun setBindViewHolder(holder: ItemViewHolder, arrayList: ArrayList<MainBean>?, position: Int) {
            val mainBean = arrayList?.get(position)
            holder.bindItem(mainBean)
        }

    }

    override fun getItemCount(): Int {
        return if (list?.size!! > 0) list.size else -1
    }

}