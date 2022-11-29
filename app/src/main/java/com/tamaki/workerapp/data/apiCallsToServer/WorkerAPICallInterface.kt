package com.tamaki.workerapp.data.apiCallsToServer

import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile

interface WorkerAPICallInterface {

    suspend fun postWorkerProfile(personalProfile: WorkerProfile): authResult<Unit>

    suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit>

    suspend fun getWorkerProfile(email:String):WorkerProfile

    suspend fun getWorkerDriversLicence(email: String): DriversLicence
}