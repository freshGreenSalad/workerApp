package com.tamaki.workerapp.data.repository

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.SupervisorSite
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.repositorys.RepositoryInterface
import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.TypeOfLicence

class FakeRepository: RepositoryInterface {



    override suspend fun presigns3(uri: Uri): String {
        return ""
    }

    override suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit> {
        return authResult.authorised()
    }

    override suspend fun postWorkerProfile(workerProfile: WorkerProfile): authResult<Unit> {
        return authResult.authorised()
    }

    override suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit> {
        return authResult.authorised()
    }

    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile): authResult<Unit> {
        return authResult.authorised()
    }

    override suspend fun postSupervisorSite(site: SupervisorSite): authResult<Unit> {
        return authResult.authorised()
    }

    override suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?> {
        return authResult.authorised(true)
    }

    override suspend fun getWorkerProfile(email: String): WorkerProfile {
        return WorkerProfile("","","","",5        )
    }

    override suspend fun getWorkerDriversLicence(email: String): DriversLicence {
        return DriversLicence(TypeOfLicence.Empty, emptyMap(),HighestClass.Class1)
    }

    override suspend fun getSupervisorProfile(): SupervisorProfile {
        return SupervisorProfile(
            email = "",
            firstName = "",
            lastName = "",
            personalPhoto = ""
        )
    }

    override suspend fun getListOfWorkerAccountsForSupervisor(): List<WorkerProfile> {
        return emptyList()
    }

    override suspend fun deleteAccount() {}
}