package com.example.mvvmretrofit.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlin.jvm.Synchronized
import com.example.mvvmretrofit.bean.Color

class DbManager {

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
    fun addColors(color: Color) {
        mDataBase?.dbDao()?.addColors(color)
    }

    //新增
    @Synchronized
    fun addColor(color: Color) {
        mDataBase?.dbDao()?.addColor(color)
    }

    //查询
    @Synchronized
    fun getColor(title: String): Color? {
        return mDataBase?.dbDao()?.getColor(title)
    }

    //删除
    @Synchronized
    fun deleteColor(color: Color) {
        mDataBase?.dbDao()?.deleteColor(color)
    }

    @Synchronized
    fun deleteColorTable() {
        mDataBase?.dbDao()?.deleteColorTable()
    }

    //修改
    @Synchronized
    fun updateColor(color: Color) {
        mDataBase?.dbDao()?.updateColor(color)
    }

    //取得Table
    @Synchronized
    fun getColors(): LiveData<List<Color>>? {
        return mDataBase?.dbDao()?.getColors()
    }

    //關閉Db
    @Synchronized
    fun closeDb() {
        mDataBase?.close()
    }

}