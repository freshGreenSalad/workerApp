package com.example.workerapp.data.apiCallsToServer

import com.example.workerapp.data.authResult
import com.example.workerapp.data.dataClasses.Profile
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.example.workerapp.data.dataClasses.ProfileInformation

interface AWSInterface {
    suspend fun getWorkerString(key: Int): String

    suspend fun postProfile(textprofile: Profile)

    //posts initial profile to dynamodb db
    suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Unit>

    //sends login details, returns authorisation and saves jwt at lower level
    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?>

    //posts the rest of the workers profile infomation once they hav completed there signup progress
    suspend fun postProfileInformation(profileInformation: ProfileInformation)
}

