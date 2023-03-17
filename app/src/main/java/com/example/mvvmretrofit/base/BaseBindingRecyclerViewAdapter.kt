package com.example.mvvmretrofit.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.*
import androidx.databinding.ObservableList.OnListChangedCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.util.AdapterUtils.ensureChangeOnMainThread
import com.example.mvvmretrofit.util.AdapterUtils.throwMissingVariable
import java.lang.ref.WeakReference

internal class BaseBindingRecyclerViewAdapter<T>(  //item界面的layout，通过databing设置
    private val baseItemView: BaseItemView<T>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val callback =
        WeakReferenceOnListChangedCallback(this) //数据list变化的时候的回调，设置在数据list中，如果list是ObservableList
    private var items //数据的list
            : List<T>? = null
    private var inflater //初始化item 界面；
            : LayoutInflater? = null

    // Currently attached recyclerview, we don't have to listen to notifications if null.
    private var recyclerView: RecyclerView? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, layoutId: Int): RecyclerView.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(viewGroup.context)
        }
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater!!, layoutId, viewGroup, false)
        val holder: RecyclerView.ViewHolder = BindingViewHolder(binding)
        binding.addOnRebindCallback(object : OnRebindCallback<ViewDataBinding>() {
            override fun onPreBind(binding: ViewDataBinding?): Boolean {
                return recyclerView != null && recyclerView!!.isComputingLayout
            }

            override fun onCanceled(binding: ViewDataBinding?) {
                if (recyclerView == null || recyclerView!!.isComputingLayout) {
                    return
                }
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position, DATA_INVALIDATION)
                }
            }
        })
        return holder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = items!![position]
        val binding = DataBindingUtil.getBinding<ViewDataBinding>(viewHolder.itemView)
        if (baseItemView.bindingVariable() != BaseItemView.ItemView.Companion.BINDING_VARIABLE_NONE) {
            val result = binding!!.setVariable(baseItemView.bindingVariable(), item)
            if (!result) {
                throwMissingVariable(
                    binding,
                    baseItemView.bindingVariable(),
                    baseItemView.layoutRes()
                )
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        if (isForDataBinding(payloads)) {
            val binding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
            binding!!.executePendingBindings()
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    fun setItems(items: List<T>?) {
        if (recyclerView != null) {
            if (this.items is ObservableList<*>) {
                (this.items as ObservableList<T>?)!!.removeOnListChangedCallback(callback)
            }
            if (items is ObservableList<*>) {
                (items as ObservableList<T>).addOnListChangedCallback(callback)
            }
        }
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (this.recyclerView == null && items != null && items is ObservableList<*>) {
            (items as ObservableList<T>).addOnListChangedCallback(callback)
        }
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (this.recyclerView != null && items != null && items is ObservableList<*>) {
            (items as ObservableList<T>).removeOnListChangedCallback(callback)
        }
        this.recyclerView = null
    }

    override fun getItemViewType(position: Int): Int {
        baseItemView.select(position, items!![position])
        return baseItemView.layoutRes()
    }

    override fun getItemId(position: Int): Long {
        return baseItemView.layoutRes().toLong()
    }

    private fun isForDataBinding(payloads: List<Any>?): Boolean {
        if (payloads == null || payloads.isEmpty()) {
            return false
        }
        for (i in payloads.indices) {
            val obj = payloads[i]
            if (obj !== DATA_INVALIDATION) {
                return false
            }
        }
        return true
    }

    private class BindingViewHolder(binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    private class WeakReferenceOnListChangedCallback<T> internal constructor(adapter: BaseBindingRecyclerViewAdapter<T>) :
        OnListChangedCallback<ObservableList<T>?>() {
        val adapterRef: WeakReference<BaseBindingRecyclerViewAdapter<T>>

        init {
            adapterRef = WeakReference(adapter)
        }

        override fun onChanged(sender: ObservableList<T>?) {
            val adapter = adapterRef.get() ?: return
            ensureChangeOnMainThread()
            adapter.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(
            sender: ObservableList<T>?,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterRef.get() ?: return
            ensureChangeOnMainThread()
            adapter.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(
            sender: ObservableList<T>?,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterRef.get() ?: return
            ensureChangeOnMainThread()
            adapter.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableList<T>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            val adapter = adapterRef.get() ?: return
            ensureChangeOnMainThread()
            for (i in 0 until itemCount) {
                adapter.notifyItemMoved(fromPosition + i, toPosition + i)
            }
        }

        override fun onItemRangeRemoved(
            sender: ObservableList<T>?,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterRef.get() ?: return
            ensureChangeOnMainThread()
            adapter.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    companion object {
        private val DATA_INVALIDATION = Any()
    }
}