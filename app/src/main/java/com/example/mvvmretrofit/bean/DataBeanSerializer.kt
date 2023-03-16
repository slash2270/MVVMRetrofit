package com.example.mvvmretrofit.bean

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.example.mvvmretrofit.DataBean
import java.io.InputStream
import java.io.OutputStream

object DataBeanSerializer: Serializer<DataBean> {
    override val defaultValue: DataBean
        get() = DataBean.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): DataBean {
        try {
            return DataBean.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: DataBean, output: OutputStream) {
        t.writeTo(output)
    }
}