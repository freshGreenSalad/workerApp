package com.example.workerapp.data.apiCallsToServer

import android.text.TextUtils
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject

class AWSRequest @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) :AWSInterface{
    override suspend fun postProfileInformation(profileInformation: ProfileInformation) {
        val jwt = dataStore.data.first()[stringPreferencesKey(name = "JWT")]
        client.post(Routes.postProfileInformation) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString<ProfileInformation>(profileInformation))
            headers {
                append("Authorization", "Bearer $jwt")
            }
        }
    }

    override suspend fun getWorkerString(key:Int): String {
        return try {
            client.get{ url(Routes.getWorkerProfileFromS3+key.toString()) }.body()
        } catch (e: Exception) {
            return "could not load worker through ktor"
        }
    }

    override suspend fun postProfile(testProfile:Profile) {
        client.post(Routes.postProfile){
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString<Profile>(testProfile))
        }
    }

    //aws visualiser route functions for workers
    // putWorkerSignupInfo
    // putWorkerSiteInfo
    // putWorkerSpecialLicence
    // putDatesWorked
    // putWorkerPersonalData
    // putWorkerExperience
    override suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequest):authResult<Unit> {
        return try {
            if (!profileLoginAuthRequest.email.isEmailValid()) {throw Exception ("email not valid")}
            if (profileLoginAuthRequest.password.length < 8) {throw Exception ("password to small")}
            val response = client.post(Routes.putWorkerSignupInfo) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }
            dataStore.edit { settings ->
                settings[stringPreferencesKey( name = "JWT")] = Json.decodeFromString<jwtTokin>(response.body<String>()).token.toString()
            }
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }
    //aws visualiser route functions for workers
    // getWorkerSignupInfo
    // getWorkerSiteInfo
    // getWorkerSpecialLicence
    // getDatesWorked
    // getWorkerPersonalData
    // getWorkerExperience
    override suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?> {
        return try {
            val respose = client.post(Routes.getWorkerSignupInfo) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }
            dataStore.edit { settings ->
                settings[stringPreferencesKey( name = "JWT")] = Json.decodeFromString<jwtTokinWithIsSupervisor>(respose.body<String>()).token.toString()
            }
            when (respose.status) {
                HttpStatusCode.OK -> authResult.authorised(Json.decodeFromString<jwtTokinWithIsSupervisor>(respose.body<String>()).isSupervisor)
                HttpStatusCode.Conflict -> authResult.unauthorised()
                else -> authResult.unauthorised()
            }

        }catch(e:Exception){
            Log.d("login", "catch block of get auth token http  request")
            authResult.unauthorised()
        }
    }
}

object Routes {
    private const val baseUrl = "http://192.168.1.151:8080/"
    //aws visualiser route functions for workers
    // putWorkerSignupInfo
    // putWorkerSiteInfo
    // putWorkerSpecialLicence
    // putDatesWorked
    // putWorkerPersonalData
    // putWorkerExperience
    const val putWorkerSignupInfo = baseUrl+"putWorkerSignupInfo"
    const val putWorkerSiteInfo = baseUrl+"putWorkerSiteInfo"
    const val putWorkerSpecialLicence = baseUrl+"putWorkerSpecialLicence"
    const val putDatesWorked = baseUrl+"putDatesWorked"
    const val putWorkerPersonalData = baseUrl+"putWorkerPersonalData"
    const val putWorkerExperience = baseUrl+"putWorkerExperience"

    //aws visualiser route functions for workers
    // getWorkerSignupInfo
    // getWorkerSiteInfo
    // getWorkerSpecialLicence
    // getDatesWorked
    // getWorkerPersonalData
    // getWorkerExperience
    const val getWorkerSignupInfo = baseUrl+"getWorkerSignupAuth"
    const val getWorkerSiteInfo = baseUrl+"getWorkerSiteInfo"
    const val getWorkerSpecialLicence = baseUrl+"getWorkerSpecialLicence"
    const val getDatesWorked = baseUrl+"getDatesWorked"
    const val getWorkerPersonalData = baseUrl+"getWorkerPersonalData"
    const val getWorkerExperience = baseUrl+"getWorkerExperience"

    //aws visualiser route functions for Supervisors
    // putSupervisorSignupInfo
    // putSupervisorSiteInfo
    // putSupervisorPersonalData
    const val putSupervisorSignupInfo = baseUrl+"putSupervisorSignupInfo"
    const val putSupervisorSiteInfo = baseUrl+"putSupervisorSiteInfo"
    const val putSupervisorPersonalData = baseUrl+"putSupervisorPersonalData"

    //aws visualiser route functions for Supervisors
    // getSupervisorSignupInfo
    // getSupervisorSiteInfo
    // getSupervisorPersonalData

    const val getSupervisorSignupInfo = baseUrl+"getSupervisorSignupInfo"
    const val getSupervisorSiteInfo = baseUrl+"getSupervisorSiteInfo"
    const val getSupervisorPersonalData = baseUrl+"getSupervisorPersonalData"

    //old routes
    const val getWorkerProfileFromS3 = baseUrl+"getInitalDataInCloud/WorkerPrimaryInfo/"
    const val postProfile = baseUrl+ "PutprofileInDynamodb"
    const val postProfileInformation = baseUrl+"postProfileInformation"
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}