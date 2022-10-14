package com.example.workerapp.data.room

import android.net.Uri
import com.example.workerapp.data.authResult
import com.example.workerapp.data.apiCallsToServer.AWSRequest
import com.example.workerapp.data.dataClasses.*
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.example.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.example.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.example.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    private var service: AWSRequest//AWSInterface = AppModule.AWSConnection(),
){

    //ktor presign S3 putImage
    suspend fun presigns3(uri: Uri):String = service.S3PresignedPut(uri)

    //DynamodbPut
    suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor):authResult<Unit> = service.postProfileAuth(profileLoginAuthRequest)
        //worker
    suspend fun postWorkerProfile(workerProfile: WorkerProfile):authResult<Unit> = service.postWorkerProfile(workerProfile)
    suspend fun postWorkerDriversLicence(licence: DriversLicence) = service.postWorkerDriversLicence(licence)
        //supervisor
    suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit> = service.postSupervisorProfile(supervisorProfile)
    suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit> = service.postSupervisorSite(site)


    //dDynamodbGet
    suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?> = service.getauthtokin(authRequest)

        //worker
    suspend fun getWorkerProfile(email:String):WorkerProfile = service.getWorkerProfile(email)
    suspend fun getWorkerDriversLicence(email: String): DriversLicence = service.getWorkerDriversLicence(email)

        //supervisor
    suspend fun getSupervisorProfile():SupervisorProfile = service.getSupervisorProfile()
    suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile> = service.getListOfWorkerAccountsForSupervisor()

    //dynamodb Delete

    suspend fun deleteAccount() = service.deleteAccount()

    //Room
    suspend fun upsert(profile: Profile) = yourDAO.upsertProfile(profile)

    suspend fun getProfile():Profile = yourDAO.getProfile()
}