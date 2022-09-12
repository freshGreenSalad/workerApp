package com.example.workerapp.data.ktor

import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest

interface AWSInterface {
    suspend fun getWorkerString(key: Int): String

    suspend fun postProfile(textprofile: Profile)

    //posts initial profile to dynamodb db
    suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Unit>

    //sends login details, returns authorisation and saves jwt at lower level
    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Unit>

    suspend fun authenticate(jwt: String)
}

