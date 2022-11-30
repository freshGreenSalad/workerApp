package com.tamaki.workerapp.userPathways.signup

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor

interface SignupAPIInterface {

    suspend fun UploadphotoTos3Bucket(uri: Uri):String

    suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit>
}