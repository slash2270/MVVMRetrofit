package com.example.mvvmretrofit.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.activity.MainActivity

class Utils {

    companion object {

        fun recycler(activity: MainActivity, recyclerView: RecyclerView, hasFixedSize: Boolean, orientation: Int) {
            recyclerView.setHasFixedSize(hasFixedSize)
            val linearLayoutManager = LinearLayoutManager(activity)
            linearLayoutManager.orientation = orientation
            recyclerView.layoutManager = linearLayoutManager
        }

    }

}