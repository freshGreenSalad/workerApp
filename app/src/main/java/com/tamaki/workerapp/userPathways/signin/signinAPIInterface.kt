package com.tamaki.workerapp.userPathways.signin

import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest

interface signinAPIInterface {

    suspend fun Login(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?>

    suspend fun deleteAccount()
}

