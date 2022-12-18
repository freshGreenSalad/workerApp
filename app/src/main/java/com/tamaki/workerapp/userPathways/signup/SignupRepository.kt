package com.tamaki.workerapp.userPathways.signup

import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import javax.inject.Inject

class SignupRepository @Inject constructor(
    private var SignupEndpoint: SignupAPI
) {
    suspend fun postWorkerExperience(experience: Experience) = SignupEndpoint.postWorkerExperience(experience = experience)

    suspend fun postWorkerProfile(workerProfile: WorkerProfile): authResult<Unit> = SignupEndpoint.postWorkerProfile(workerProfile)

    suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit> = SignupEndpoint.postWorkerDriversLicence(licence)
}