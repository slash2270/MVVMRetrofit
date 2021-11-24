package com.example.mvvmretrofit

import androidx.lifecycle.MutableLiveData

class ABC {
    private var students: MutableLiveData<ArrayList<MainBean>>? = null
    fun getStudents(): MutableLiveData<ArrayList<MainBean>>? {
        if (students == null) {
            students = MutableLiveData()
            students?.value = ArrayList()
        }
        return students
    }

    fun aVoid() {}
}