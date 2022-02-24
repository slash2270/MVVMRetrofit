package com.example.mvvmretrofit.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color")
class Color(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name = "id") val id: Int,
    @ColumnInfo (name = "title") val title: String,
    @ColumnInfo (name = "thumbnailUrl") val thumbnailUrl: String
)