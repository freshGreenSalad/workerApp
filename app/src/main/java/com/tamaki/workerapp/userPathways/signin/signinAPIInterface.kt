package com.tamaki.workerapp.userPathways.signin

import android.net.Uri
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor

interface signinAPIInterface {

    suspend fun Login(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?>

    suspend fun deleteAccount()
}

