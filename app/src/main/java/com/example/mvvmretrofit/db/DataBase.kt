package com.example.mvvmretrofit.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmretrofit.bean.Color

@Database(entities = [Color::class], version = 2, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun dbDao(): DbDao
}