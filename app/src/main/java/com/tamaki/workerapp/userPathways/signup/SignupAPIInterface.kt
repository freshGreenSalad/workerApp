package com.tamaki.workerapp.userPathways.signup

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.EmailPasswordIsSupervisorPushId
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile

interface SignupAPIInterface {

    suspend fun UploadphotoTos3Bucket(uri: Uri):String

    suspend fun postProfileAuth(profileLoginAuthRequest: EmailPasswordIsSupervisorPushId): authResult<Unit>

    suspend fun postWorkerExperience(experience: Experience): authResult<Unit>

    suspend fun postWorkerProfile(personalProfile: WorkerProfile): authResult<Unit>

    suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit>
}