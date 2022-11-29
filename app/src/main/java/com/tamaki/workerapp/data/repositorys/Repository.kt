package com.tamaki.workerapp.data.room

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    private var service: AWSRequest//AWSInterface = AppModule.AWSConnection(),
): RepositoryInterface {

    //ktor presign S3 putImage
    override suspend fun presigns3(uri: Uri):String = service.S3PresignedPut(uri)

    //DynamodbPut
    override suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor):authResult<Unit> = service.postProfileAuth(profileLoginAuthRequest)
        //worker
    override suspend fun postWorkerProfile(workerProfile: WorkerProfile):authResult<Unit> = service.postWorkerProfile(workerProfile)
    override suspend fun postWorkerDriversLicence(licence: DriversLicence):authResult<Unit> = service.postWorkerDriversLicence(licence)
        //supervisor
    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit> = service.postSupervisorProfile(supervisorProfile)
    override suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit> = service.postSupervisorSite(site)


    //dDynamodbGet
    override suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?> = service.getauthtokin(authRequest)

        //worker
    override suspend fun getWorkerProfile(email:String):WorkerProfile = service.getWorkerProfile(email)
    override suspend fun getWorkerDriversLicence(email: String): DriversLicence = service.getWorkerDriversLicence(email)

        //supervisor
    override suspend fun getSupervisorProfile():SupervisorProfile = service.getSupervisorProfile()
    override suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile> = service.getListOfWorkerAccountsForSupervisor()

    //dynamodb Delete

    override suspend fun deleteAccount() = service.deleteAccount()

    //Room
    suspend fun upsert(profile: Profile) = yourDAO.upsertProfile(profile)

    suspend fun getProfile():Profile = yourDAO.getProfile()
}