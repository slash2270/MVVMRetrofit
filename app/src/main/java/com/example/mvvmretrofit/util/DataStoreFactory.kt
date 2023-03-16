package com.example.mvvmretrofit.util

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.mvvmretrofit.DataBean
import com.example.mvvmretrofit.bean.DataBeanSerializer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ConcurrentHashMap

object DataStoreFactory {

    private const val USER_PREFERENCES = "default_user_preferences"
    private lateinit var defaultDataStore: DataStore<androidx.datastore.preferences.core.Preferences>
    private val dataStoreMaps = ConcurrentHashMap<String, DataStore<androidx.datastore.preferences.core.Preferences>>()
    private val applicationScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            Log.d(
                "",
                "applicationScope:\n${throwable.message.toString()}", throwable
            )
        })

    val Context.beanDataStore : DataStore<DataBean> by dataStore(
        fileName = USER_PREFERENCES,
        serializer = DataBeanSerializer
    )

    fun init(appContext: Context) {
        getDefaultPreferencesDataStore(appContext)
    }

    private fun getDefaultPreferencesDataStore(appContext: Context): DataStore<androidx.datastore.preferences.core.Preferences> {
        if (this::defaultDataStore.isInitialized.not()) {
            defaultDataStore = createPreferencesDataStore(appContext, USER_PREFERENCES)
        }
        return defaultDataStore
    }

    fun getDefaultPreferencesDataStore() = defaultDataStore

    fun getPreferencesDataStore(
        appContext: Context,
        name: String
    ): DataStore<androidx.datastore.preferences.core.Preferences> =
        dataStoreMaps.getOrPut(name) {
            createPreferencesDataStore(appContext, name)
        }

    private fun createPreferencesDataStore(
        appContext: Context,
        name: String
    ): DataStore<androidx.datastore.preferences.core.Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(
                SharedPreferencesMigration(
                    appContext,
                    name
                )
            ),
            applicationScope,
            produceFile = { appContext.preferencesDataStoreFile(name) }
        )
    }
}