package com.example.mvvmretrofit.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlin.jvm.Synchronized
import com.example.mvvmretrofit.bean.ColorBean

class ColorRepository {

    @Volatile
    private var mDataBase: DataBase? = null
    fun createDb(context: Context): DataBase? {
        val tempDB = mDataBase
        if(tempDB != null){
            return tempDB
        }
        synchronized(this){
            mDataBase = Room.databaseBuilder(context, DataBase::class.java,"database.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()    // 清除資料
                .build()
            return mDataBase
        }
    }

    //新增
    @Synchronized
    fun addColors(colorBean: ColorBean) {
        mDataBase?.dbDao()?.addColors(colorBean)
    }

    //新增
    @Synchronized
    fun addColor(colorBean: ColorBean) {
        mDataBase?.dbDao()?.addColor(colorBean)
    }

    //查询
    @Synchronized
    fun getColor(title: String): ColorBean? {
        return mDataBase?.dbDao()?.getColor(title)
    }

    //删除
    @Synchronized
    fun deleteColor(colorBean: ColorBean) {
        mDataBase?.dbDao()?.deleteColor(colorBean)
    }

    @Synchronized
    fun deleteColorTable() {
        mDataBase?.dbDao()?.deleteColorTable()
    }

    //修改
    @Synchronized
    fun updateColor(colorBean: ColorBean) {
        mDataBase?.dbDao()?.updateColor(colorBean)
    }

    //取得Table
    @Synchronized
    fun getColors(): LiveData<List<ColorBean>>? {
        return mDataBase?.dbDao()?.getColors()
    }

    //關閉Db
    @Synchronized
    fun closeDb() {
        mDataBase?.close()
    }

}