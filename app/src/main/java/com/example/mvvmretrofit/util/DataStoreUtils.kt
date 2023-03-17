package com.example.mvvmretrofit.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.example.mvvmretrofit.util.DataStoreFactory.beanDataStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException

class DataStoreUtils() {

//    companion object Factory{
//
//    }

    private val dataStore: DataStore<Preferences> = DataStoreFactory.getDefaultPreferencesDataStore()
    private val dataStoreData = dataStore.data
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun putString(key: String, value: String?) = putData(stringPreferencesKey(key), value)

    fun putStringSet(key: String, values: Set<String>?) = putData(stringSetPreferencesKey(key), values)

    fun putInt(key: String, value: Int) = putData(intPreferencesKey(key), value)

    fun putLong(key: String, value: Long) = putData(longPreferencesKey(key), value)

    fun putFloat(key: String, value: Float) = putData(floatPreferencesKey(key), value)

    fun putBoolean(key: String, value: Boolean) = putData(booleanPreferencesKey(key), value)

    fun getString(key: String, defValue: String?): String? = readNullableData(stringPreferencesKey(key), defValue)

    fun getStringSet(key: String, defValues: Set<String>?): Set<String>? = readNullableData(stringSetPreferencesKey(key), defValues)

    fun getIntSync(key: String, defValue: Int): Unit = readNonNullData(intPreferencesKey(key), defValue)

    fun getLong(key: String, defValue: Long): Unit = readNonNullData(longPreferencesKey(key), defValue)

    fun getFloat(key: String, defValue: Float): Unit = readNonNullData(floatPreferencesKey(key), defValue)

    fun getBooleanSync(key: String, defValue: Boolean): Unit = readNonNullData(booleanPreferencesKey(key), defValue)

    suspend fun getStringFlow(key: String, defValue: String): Flow<String> = readNonNullFlowData(stringPreferencesKey(key), defValue)

    suspend fun getStringSetFlow(key: String, defValues: Set<String>): Flow<Set<String>> = readNonNullFlowData(stringSetPreferencesKey(key), defValues)

    suspend fun getIntFlow(key: String, defValue: Int): Flow<Int> = readNonNullFlowData(intPreferencesKey(key), defValue)

    suspend fun getLongFlow(key: String, defValue: Long): Flow<Long> = readNonNullFlowData(longPreferencesKey(key), defValue)

    suspend fun getFloatFlow(key: String, defValue: Float): Flow<Float> = readNonNullFlowData(floatPreferencesKey(key), defValue)

    suspend fun getBooleanFlow(key: String, defValue: Boolean): Flow<Boolean> = readNonNullFlowData(booleanPreferencesKey(key), defValue)

    fun getInt(key: String, defValue: Int, block: (Int) -> Unit) = readEmitData(intPreferencesKey(key), defValue, block)

    fun getBoolean(key: String, defValue: Boolean, block: (Boolean) -> Unit) = readEmitData(booleanPreferencesKey(key), defValue, block)

    fun getString(key: String, defValue: String?, block: (String?) -> Unit) = readEmitNullData(stringPreferencesKey(key), defValue, block)

    fun getStringSet(key: String, defValues: Set<String>?, block: (Set<String>?) -> Unit) = readEmitNullData(stringSetPreferencesKey(key), defValues, block)

    fun getLong(key: String, defValue: Long, block: (Long) -> Unit) = readEmitData(longPreferencesKey(key), defValue, block)

    fun getFloat(key: String, defValue: Float, block: (Float) -> Unit) = readEmitData(floatPreferencesKey(key), defValue, block)

    private fun <T> readNonNullData(key: Preferences.Key<T>, defValue: T) {
        return runBlocking {
            dataStoreData.map {
                it[key] ?: defValue
            }.first()
        }
    }

    private fun <T> readNullableData(key: Preferences.Key<T>, defValue: T?): T? {
        return runBlocking {
            dataStoreData.map {
                it[key] ?: defValue
            }.firstOrNull()
        }
    }

    private suspend fun <T> readNullableEmitFlowData(
        key: Preferences.Key<T>,
        defValue: T?
    ): Flow<T?> {
        return dataStoreData.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[key] ?: defValue
        }
    }

    private fun <T> putData(key: Preferences.Key<T>, value: T?) {
        scope.launch {
            dataStore.edit {
                if (value != null) it[key] = value else it.remove(key)
            }
        }
    }

    fun <T> removeData(key: String, value: T?) {
        scope.launch {
            dataStore.edit {
                if (value != null) it.remove(stringPreferencesKey(key))
            }
        }
    }

    fun clearData() {
        scope.launch {
            dataStore.edit {
                it.clear()
            }
        }
    }

    private suspend fun <T> readNonNullFlowData(key: Preferences.Key<T>, defValue: T): Flow<T> {
        return dataStoreData.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[key] ?: defValue
        }
    }

    private fun <T> readEmitData(key: Preferences.Key<T>, defValue: T, block: (T) -> Unit) {
        scope.launch {
            readNonNullFlowData(key, defValue).collectLatest {
                block(it)
            }
        }
    }

    private fun <T> readEmitNullData(
        key: Preferences.Key<T>,
        defValue: T?,
        block: (T?) -> Unit
    ) {
        scope.launch {
            readNullableEmitFlowData(key, defValue).collectLatest {
                block(it)
            }
        }
    }

    suspend fun updateDataStore(context: Context) {
        //context.beanDataStore.data.first() // 讀取方式
        update(context) // 寫入方式
    }

    private suspend fun update(context: Context) {
        context.beanDataStore.updateData { data ->
            data.toBuilder().setId("Id").setTitle("Title").setThumbnailUrl("Content").build()
        }
    }

}