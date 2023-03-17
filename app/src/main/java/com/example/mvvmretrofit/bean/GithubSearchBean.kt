package com.example.mvvmretrofit.bean

import com.google.gson.annotations.SerializedName
import java.util.Arrays

data class GithubSearchBean(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("html_url") var htmlUrl: String?,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val starCount: Int?,
)

data class Owner(
    @SerializedName("avatar_url") val avatar_url: String?,
)