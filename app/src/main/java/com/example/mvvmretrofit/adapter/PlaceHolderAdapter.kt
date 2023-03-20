package com.example.mvvmretrofit.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.bean.ColorBean
import com.example.mvvmretrofit.databinding.ItemPlaceholderBinding
import com.example.mvvmretrofit.util.CustomWatermarkTransformation

class PlaceHolderAdapter(private val list: ArrayList<ColorBean>?, private val context: Context) : RecyclerView.Adapter<ItemViewHolder>() {

    private lateinit var binding: ItemPlaceholderBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_placeholder, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        AddBindViewHolder().setBindViewHolder(holder, list, position)
    }

    inner class AddBindViewHolder {
        fun setBindViewHolder(holder: ItemViewHolder, arrayList: ArrayList<ColorBean>?, position: Int) {
            val item = arrayList?.get(position)
            holder.bindItem(item)
            binding.color.load(item?.url){
                transformations(CustomWatermarkTransformation("Slash", context.resources.getColor(R.color.white), 20.0f)) //浮水印
                listener(
                    onStart ={ request ->
                        Log.d("lpf", "PlaceHolder onError 開始加載...")
                    },
                    onError = { request, throwable ->
                        Log.d("lpf", "PlaceHolder onError 加載失敗...")
                    },
                    onCancel = { request ->
                        Log.d("lpf", "PlaceHolder onCancel 加載重載中...")
                    },
                    onSuccess = { request, metadata ->
                        Log.d("lpf", "PlaceHolder onSuccess 加載成功...")
                    }
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return if (list?.size!! > 0) list.size else 0
    }

}