package com.tamaki.workerapp.userPathways.Worker.API

import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile

interface WorkerAPICallInterface {

    suspend fun postWorkerProfile(personalProfile: WorkerProfile): authResult<Unit>

    suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit>

    suspend fun getWorkerProfile(email:String):WorkerProfile

    suspend fun getWorkerDriversLicence(email: String): DriversLicence
}