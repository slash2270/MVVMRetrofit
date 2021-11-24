package com.example.mvvmretrofit.db

import android.content.Context
import androidx.room.Room
import kotlin.jvm.Synchronized
import com.example.mvvmretrofit.bean.ColorBean

object DbManager {

    private lateinit var mDataBase: DataBase

    @Synchronized
    fun createDb(context: Context) {
        mDataBase = Room.databaseBuilder(context, DataBase::class.java,"database.db")
            .allowMainThreadQueries()
            .build()
    }

    //新增
    @Synchronized
    fun addColors(listColors: List<ColorBean>?) {
        mDataBase.dbDao().addColors(listColors)
    }

    //新增
    @Synchronized
    fun addColor(colorBean: ColorBean) {
        mDataBase.dbDao().addColor(colorBean)
    }

    //查询
    @Synchronized
    fun getColor(title: String): ColorBean {
        return mDataBase.dbDao().getColor(title)
    }

    //删除
    @Synchronized
    fun deleteColor(colorBean: ColorBean) {
        mDataBase.dbDao().deleteColor(colorBean)
    }

    @Synchronized
    fun deleteColorTable() {
        mDataBase.dbDao().deleteColorTable()
    }

    //修改
    @Synchronized
    fun updateColor(colorBean: ColorBean) {
        mDataBase.dbDao().updateColor(colorBean)
    }

    //取得Table
    @Synchronized
    fun getColors(): List<ColorBean>? {
        return mDataBase.dbDao().getColors()
    }

    //關閉Db
    @Synchronized
    fun closeDb() {
        mDataBase.close()
    }

}