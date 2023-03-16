package com.example.mvvmretrofit.bean

import com.google.gson.annotations.SerializedName

data class GithubSearchBean(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") var htmlUrl: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val starCount: Int
)