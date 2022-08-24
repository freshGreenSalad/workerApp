package com.example.workerapp.data.room

import com.example.workerapp.data.AppModule
import com.example.workerapp.data.ktor.AWSInterface
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    var service: AWSInterface = AppModule.AWSConnection(),
){
    suspend fun getworkerstring(index:Int) = service.getWorkerString(index)

}