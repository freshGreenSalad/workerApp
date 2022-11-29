package com.tamaki.workerapp.data.apiCallsToServer

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile

interface AWSInterface {

    suspend fun UploadphotoTos3Bucket(uri: Uri):String

    suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit>

    suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit>

    suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit>

    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?>

    suspend fun getSupervisorProfile():SupervisorProfile

    suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile>

    suspend fun deleteAccount()
}

