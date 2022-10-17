package com.tamaki.workerapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class DataStoreImplementaion @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DatastoreInterface {
    override suspend fun deleteAccount() {
        dataStore.edit { it.clear() }
    }
}