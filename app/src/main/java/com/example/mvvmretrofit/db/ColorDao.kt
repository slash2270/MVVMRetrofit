package com.example.mvvmretrofit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmretrofit.bean.ColorBean

@Dao
interface ColorDao {

    //插入
    @Insert(onConflict = 5)
    fun addColors(vararg colorBean: ColorBean)

    /*@Insert
    fun addColors(listColors: List<ColorBean>?)*/

    @Insert(onConflict = 5)
    fun addColor(colorBean: ColorBean)

    //修改
    @Update(onConflict = 1)
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
    @Query("SELECT * FROM color ORDER BY id ASC")
    fun getColors(): LiveData<List<ColorBean>>

}