package com.example.mvvmretrofit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvmretrofit.bean.Color

@Dao
interface DbDao {

    //插入
    @Insert(onConflict = 5)
    fun addColors(vararg color: Color)

    /*@Insert
    fun addColors(listColors: List<ColorBean>?)*/

    @Insert(onConflict = 5)
    fun addColor(color: Color)

    //修改
    @Update(onConflict = 1)
    fun updateColor(color: Color)

    //删除
    @Delete
    fun deleteColor(color: Color)

    @Query("DELETE FROM color")
    fun deleteColorTable()

    //取得Color
    @Query("SELECT * FROM color WHERE title=:title")
    fun getColor(title: String): Color

    //查詢
    @Query("SELECT * FROM color ORDER BY id ASC")
    fun getColors(): LiveData<List<Color>>

}