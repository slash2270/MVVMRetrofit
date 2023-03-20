package com.example.mvvmretrofit.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "color")
data class ColorBean(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")@ColumnInfo (name = "id") val id: Int,
    @SerializedName("title")@ColumnInfo (name = "title") val title: String,
    @SerializedName("url")@ColumnInfo (name = "url") val url: String,
    @SerializedName("thumbnailUrl")@ColumnInfo (name = "thumbnailUrl") val thumbnailUrl: String
)