package com.example.mvvmretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.databinding.ItemTitleBinding
import com.example.mvvmretrofit.databinding.RecyclerDynamicBinding
import com.example.mvvmretrofit.databinding.RecyclerPageBinding
import com.example.mvvmretrofit.databinding.RecyclerPlaceholderBinding
import com.example.mvvmretrofit.db.ColorRepository
import com.example.mvvmretrofit.model.*
import com.example.mvvmretrofit.util.Utils

class RvAdapter(private val activity: MainActivity, private val colorRepository: ColorRepository) : RecyclerView.Adapter<ItemViewHolder>() {

    private lateinit var viewDataBinding: ViewDataBinding

    companion object {
        const val VIEW_TYPE_ZERO = 0
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        const val VIEW_TYPE_Three = 3
        const val VIEW_TYPE_Four = 4
        const val VIEW_TYPE_Five = 5
        const val VIEW_TYPE_Six = 6
        const val VIEW_TYPE_Seven = 7
        const val VIEW_TYPE_Eight = 8
        const val VIEW_TYPE_Nine = 9
        const val VIEW_TYPE_Ten = 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        viewDataBinding = when(getItemViewType(viewType)){
            VIEW_TYPE_ZERO -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_title, parent, false)
            }
            VIEW_TYPE_ONE -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_TWO -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_page, parent, false)
            }
            VIEW_TYPE_Three -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_dynamic, parent, false)
            }
            VIEW_TYPE_Four -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Five -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Six -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Seven -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Eight -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Nine -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            VIEW_TYPE_Ten -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
            else -> {
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recycler_placeholder, parent, false)
            }
        }
        return ItemViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when(holder.itemViewType){
            VIEW_TYPE_ZERO -> {
                val itemTitleBinding = viewDataBinding as ItemTitleBinding
                itemTitleBinding.model = TitleViewModel()
            }
            VIEW_TYPE_ONE -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_TWO -> {
                val pageViewModel = PagingViewModel()
                val recyclerPageBinding = viewDataBinding as RecyclerPageBinding
                Utils.recycler(activity, recyclerPageBinding.recyclerViewPage, true, LinearLayoutManager.VERTICAL)
                pageViewModel.data(activity, recyclerPageBinding)
                recyclerPageBinding.model = pageViewModel
            }
            VIEW_TYPE_Three -> {
                val recyclerDynamicBinding = viewDataBinding as RecyclerDynamicBinding
                recyclerDynamicBinding.viewModel = DynamicViewModel(activity)
                recyclerDynamicBinding.uiViewModel = DynamicUiViewModel(activity)
            }
            VIEW_TYPE_Four -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Five -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Six -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Seven -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Eight -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Nine -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            VIEW_TYPE_Ten -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
            else -> {
                val placeHolderViewModel = PlaceHolderViewModel()
                val recyclerPlaceholderBinding = viewDataBinding as RecyclerPlaceholderBinding
                Utils.recycler(activity, recyclerPlaceholderBinding.recyclerViewPlaceHolder, true, LinearLayoutManager.VERTICAL)
                placeHolderViewModel.data(activity, recyclerPlaceholderBinding, colorRepository)
                recyclerPlaceholderBinding.model = placeHolderViewModel
            }
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> VIEW_TYPE_ZERO
            1 -> VIEW_TYPE_ONE
            2 -> VIEW_TYPE_TWO
            3 -> VIEW_TYPE_Three
            4 -> VIEW_TYPE_Four
            5 -> VIEW_TYPE_Five
            6 -> VIEW_TYPE_Six
            7 -> VIEW_TYPE_Seven
            8 -> VIEW_TYPE_Eight
            9 -> VIEW_TYPE_Nine
            10 -> VIEW_TYPE_Ten
            else -> VIEW_TYPE_ONE
        }
    }

}