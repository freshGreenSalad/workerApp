package com.tamaki.workerapp.data.wrapperClasses

interface DatastorePreferancesInterface {

    suspend fun read(key: String): String?

    suspend fun edit(key: String)
}