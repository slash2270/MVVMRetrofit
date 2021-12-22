package com.example.mvvmretrofit.db

import androidx.room.*
import com.example.mvvmretrofit.bean.ColorBean

@Dao
interface DbDao {

    //插入
    @Insert
    fun addColors(vararg colorBean: ColorBean)

    /*@Insert
    fun addColors(listColors: List<ColorBean>?)*/

    @Insert
    fun addColor(colorBean: ColorBean)

    //修改
    @Update
    fun updateColor(colorBean: ColorBean)

    //删除
    @Delete
    fun deleteColor(colorBean: ColorBean)

    @Query("DELETE FROM color")
    fun deleteColorTable()

    //取得Color
    @Query("SELECT * FROM color WHERE title=:title")
    fun getColor(title: String): ColorBean

    //查詢
    @Query("SELECT * FROM color")
    fun getColors(): List<ColorBean>?

}