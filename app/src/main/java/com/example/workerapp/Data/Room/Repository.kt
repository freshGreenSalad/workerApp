package com.example.workerapp.Data.Room

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: dataAccessObject,
){
    suspend fun upsert(workers: Workers) {
        yourDAO.upsert(workers)
    }

    fun getWorkers() = yourDAO.getWorkers()

}