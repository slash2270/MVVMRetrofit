package com.example.mvvmretrofit.model

import android.text.TextUtils
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import com.example.mvvmretrofit.BR
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.base.BaseItemView
import com.example.mvvmretrofit.base.BaseItemView.ItemViewSelector

class DynamicViewModel(var baseActivity: MainActivity) {

    @JvmField
    val dataItems: ObservableList<DynamicItemViewModel> = ObservableArrayList()
    var positionStr: String? = null

    @JvmField
    var mViewSelector: ItemViewSelector<DynamicItemViewModel> = object : ItemViewSelector<DynamicItemViewModel> {
        override fun select(
            itemView: BaseItemView.ItemView?,
            position: Int,
            item: DynamicItemViewModel
        ) {
            itemView?.set(BR.viewModel,
                if (item.type == 1) R.layout.recycler_dynamic_item_one else R.layout.recycler_dynamic_item_two
            )
        }

        override fun viewTypeCount(): Int {
            return 2
        }
    }

    init {
        for (i in 0..19) {
            if (i % 2 == 1) {
                dataItems.add(DynamicItemViewModel(baseActivity, 1, i.toString()))
            } else {
                dataItems.add(DynamicItemViewModel(baseActivity, 2, i.toString()))
            }
        }
    }

    fun add() {
        if (TextUtils.isEmpty(positionStr) || dataItems.size < positionStr!!.toInt()) return
        dataItems.add(positionStr!!.toInt(), DynamicItemViewModel(baseActivity, 1, "增加"))
    }

    fun delete() {
        if (TextUtils.isEmpty(positionStr) || dataItems.size <= positionStr!!.toInt()) return
        dataItems.removeAt(positionStr!!.toInt())
    }

    fun change() {
        if (TextUtils.isEmpty(positionStr) || dataItems.size <= positionStr!!.toInt()) return
        val dynamicItemViewModel = dataItems[positionStr!!.toInt()]
        dynamicItemViewModel.text.set("修改")
    }
}