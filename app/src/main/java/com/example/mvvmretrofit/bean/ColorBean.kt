package com.example.mvvmretrofit.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color")
class ColorBean(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String?
)