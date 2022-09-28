package com.example.workerapp.data.room

import com.example.workerapp.data.authResult
import com.example.workerapp.data.apiCallsToServer.AWSRequest
import com.example.workerapp.data.dataClasses.Profile
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.example.workerapp.data.dataClasses.ProfileInformation
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    private var service: AWSRequest//AWSInterface = AppModule.AWSConnection(),
){
    suspend fun getworkerstring(index:Int) = service.getWorkerString(index)

    suspend fun postprofile(profile: Profile) = service.postProfile(profile)

    //Dynamodb
    suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequest):authResult<Unit> = service.postProfileAuth(profileLoginAuthRequest)

    suspend fun login(authRequest: ProfileLoginAuthRequest): authResult<Boolean?> = service.getauthtokin(authRequest)

    suspend fun PostProfileInformation(profileInformation: ProfileInformation) = service.postProfileInformation(profileInformation)

    //Room
    suspend fun upsert(profile: Profile) = yourDAO.upsertProfile(profile)

    suspend fun getProfile():Profile = yourDAO.getProfile()


}