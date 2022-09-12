package com.example.workerapp.data.room

import android.content.Context
import com.example.workerapp.data.authResult
import com.example.workerapp.data.ktor.AWSRequest
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import javax.inject.Inject

class YourRepository @Inject constructor(
    private val yourDAO: DataAccessObject,
    private var service: AWSRequest//AWSInterface = AppModule.AWSConnection(),
){
    suspend fun getworkerstring(index:Int) = service.getWorkerString(index)

    suspend fun postprofile(profile: Profile) = service.postProfile(profile)

    //Dynamodb
    suspend fun postAuthProfile(profileLoginAuthRequest: ProfileLoginAuthRequest):authResult<Unit> = service.postProfileAuth(profileLoginAuthRequest)

    suspend fun login(authRequest: ProfileLoginAuthRequest, context: Context): authResult<Unit> = service.getauthtokin(authRequest)

    suspend fun authenticate(jwt:String) = service.authenticate(jwt)

    //Room
    suspend fun upsert(profile: Profile) = yourDAO.upsertProfile(profile)

    suspend fun getProfile():Profile = yourDAO.getProfile()


}