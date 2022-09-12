package com.example.workerapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

//decided not to use this as its a bit complex
class DataStoreManager(val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "USER_DATASTORE")

    companion object {
        val NAME = stringPreferencesKey("tokin")
    }

    suspend fun savetoDataStore(jwt: String) {
        context.dataStore.edit {
            it[NAME] = jwt
        }
    }

    suspend fun getFromDataStore() = context.dataStore.data.map {
            val name = it[NAME] ?: ""
    }
}
