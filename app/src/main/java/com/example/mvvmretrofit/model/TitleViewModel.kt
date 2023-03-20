package com.example.mvvmretrofit.model

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.mvvmretrofit.util.DataStoreUtils

class TitleViewModel : ViewModel() {

    val id = ObservableField("")
    val title = ObservableField("")
    val content = ObservableField("")
    val color = ObservableField("")

    init {
        DataModel().getTitle()
        val dataStoreUtils = DataStoreUtils()
        id.set(dataStoreUtils.getString("id", "Id"))
        title.set(dataStoreUtils.getString("title", "Title"))
        content.set(dataStoreUtils.getString("content", "Content"))
        color.set(dataStoreUtils.getString("color", "Color"))
//        viewModelScope.launch(Dispatchers.IO){
//            dataStoreUtils.updateDataStore(activity)
//            val data = activity.beanDataStore.data.first()
//            id.set(data.id)
//            title.set(data.title)
//            content.set(data.thumbnailUrl)
//            dataStoreUtils.removeData("id", data.id)
//            dataStoreUtils.removeData("title", data.title)
//            dataStoreUtils.removeData("content", data.thumbnailUrl)
//        }
    }

}