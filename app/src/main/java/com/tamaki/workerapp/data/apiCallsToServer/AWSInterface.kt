package com.tamaki.workerapp.data.apiCallsToServer

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile

interface AWSInterface {

    //important presign function
    suspend fun S3PresignedPut(uri: Uri):String

    //-----------------------------------------------------------------------------------------
    //aws put functions
    suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit>

        //worker
    suspend fun postWorkerProfile(personalProfile: WorkerProfile):authResult<Unit>
    suspend fun postWorkerDriversLicence(licence: DriversLicence):authResult<Unit>

        //supervisor
    suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit>
    suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit>

    //-----------------------------------------------------------------------------------------
    //aws get functions
    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?>

        //worker
    suspend fun getWorkerProfile(email:String):WorkerProfile
    suspend fun getWorkerDriversLicence(email: String): DriversLicence

        //supervisor
    suspend fun getSupervisorProfile():SupervisorProfile
    suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile>

    //------------------------------------------------------------------------------------------
    suspend fun deleteAccount()

}

