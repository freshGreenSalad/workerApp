package com.tamaki.workerapp.data.repositorys

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.SupervisorSite
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile

interface RepositoryInterface {

    suspend fun presigns3(uri: Uri):String

    suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit>

    suspend fun postWorkerProfile(workerProfile: WorkerProfile):authResult<Unit>
    suspend fun postWorkerDriversLicence(licence: DriversLicence):authResult<Unit>
    //supervisor
    suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit>
    suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit>


    //dDynamodbGet
    suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?>

    //worker
    suspend fun getWorkerProfile(email:String): WorkerProfile
    suspend fun getWorkerDriversLicence(email: String): DriversLicence

    //supervisor
    suspend fun getSupervisorProfile(): SupervisorProfile
    suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile>

    //dynamodb Delete

    suspend fun deleteAccount()

}