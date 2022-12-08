package com.tamaki.workerapp.data.room

import android.net.Uri
import com.tamaki.workerapp.data.apiCallsToServer.LoginAPICalls
import com.tamaki.workerapp.userPathways.Supervisor.API.SupervisorAPICalls
import com.tamaki.workerapp.userPathways.Worker.API.WorkerAPICalls
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.userPathways.signup.SignupAPI
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    private var service: LoginAPICalls,
    private var workerEnpoints: WorkerAPICalls,
    private var SupervisorEndpoint: SupervisorAPICalls,
    private var SignupEndpoint: SignupAPI
): RepositoryInterface {

    override suspend fun presigns3(uri: Uri):String = SignupEndpoint.UploadphotoTos3Bucket(uri)

    override suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor):authResult<Unit> = SignupEndpoint.postProfileAuth(profileLoginAuthRequest)

    override suspend fun postWorkerProfile(workerProfile: WorkerProfile):authResult<Unit> = workerEnpoints.postWorkerProfile(workerProfile)

    override suspend fun postWorkerDriversLicence(licence: DriversLicence):authResult<Unit> = workerEnpoints.postWorkerDriversLicence(licence)

    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile):authResult<Unit> = SupervisorEndpoint.postSupervisorProfile(supervisorProfile)

    override suspend fun postSupervisorSite(site: SupervisorSite):authResult<Unit> = SupervisorEndpoint.postSupervisorSite(site)

    override suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?> = service.Login(authRequest)

    override suspend fun getWorkerProfile(email:String):WorkerProfile = workerEnpoints.getWorkerProfile(email)

    override suspend fun getWorkerDriversLicence(email: String): DriversLicence = workerEnpoints.getWorkerDriversLicence(email)

    override suspend fun getSupervisorProfile():SupervisorProfile = SupervisorEndpoint.getSupervisorProfile()

    override suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile> = SupervisorEndpoint.getListOfWorkerAccountsForSupervisor()

    override suspend fun deleteAccount() = service.deleteAccount()

    suspend fun upsert(profile: Profile) = yourDAO.upsertProfile(profile)

    suspend fun getProfile():Profile = yourDAO.getProfile()
}