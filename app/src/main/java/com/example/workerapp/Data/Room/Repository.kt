package com.example.workerapp.Data.Room

import com.example.workerapp.Data.Room.ktor.AWSInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: dataAccessObject,
    var service: AWSInterface = AppModuel.AWSConnection(),
){
    suspend fun upsert(workers: Workers) {
        yourDAO.upsert(workers)
    }

    fun getWorkers() = yourDAO.getWorkers()

    suspend fun getworkerstring(index:Int) = service.getWorkerString(index)

}