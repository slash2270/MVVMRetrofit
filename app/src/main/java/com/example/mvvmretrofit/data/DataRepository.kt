package com.example.mvvmretrofit.data

import com.example.mvvmretrofit.bean.GithubSearchBean
import com.google.gson.annotations.SerializedName

class DataRepository {

    @SerializedName("total_count")
    var totalCount: Int = 0
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    @SerializedName("items")
    var items: List<GithubSearchBean> = emptyList()

}