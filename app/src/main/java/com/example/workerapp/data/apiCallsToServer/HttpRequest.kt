package com.example.workerapp.data.apiCallsToServer

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.workerapp.data.authResult
import com.example.workerapp.data.dataClasses.*
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.example.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.example.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.example.workerapp.data.dataClasses.supervisorDataClasses.supervisorProfileFail
import com.example.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.example.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.example.workerapp.data.dataClasses.workerDataClasses.licenceFail
import com.example.workerapp.data.dataClasses.workerDataClasses.workerProfileFail
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

import javax.inject.Inject

class AWSRequest @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : AWSInterface {

    private suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    //important preSigning function
    override suspend fun S3PresignedPut(uri: Uri): String {
        Log.d("top of presign put", "")
        val jwt = read("JWT")!!

        val presign = try {
            client.put(Routes.presignPutRequest) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString("profilePicture"))
                bearerAuth(jwt)
            }.body()
        } catch (e: Exception) {
            Log.d("main", "failed to get presign link")
            ""
        }

        try {
            val bodydata = File(uri.path!!).readBytes()

            client.put(presign) {
                contentType(ContentType.MultiPart.FormData)
                setBody(bodydata)
            }
        } catch (e: Exception) {
            Log.d("AWS", "Failed to add image to AWS")
        }

        return presign.split("?")[0]
    }

    //aws visualiser route functions for workers
// putWorkerSignupInfo
// putWorkerSiteInfo
// putWorkerSpecialLicence
// putDatesWorked
// putWorkerPersonalData
// putWorkerExperience
    override suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit> {
        return try {
            if (!profileLoginAuthRequest.email.isEmailValid()) {
                throw Exception("email not valid")
            }
            if (profileLoginAuthRequest.password.length < 8) {
                throw Exception("password to small")
            }
            val response = client.post(Routes.putWorkerSignupInfo) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(
                        profileLoginAuthRequest
                    )
                )
            }
            dataStore.edit { settings ->
                settings[stringPreferencesKey(name = "JWT")] =
                    Json.decodeFromString<jwtTokinWithIsSupervisor>(response.body()).token
            }
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun postWorkerProfile(personalProfile: WorkerProfile): authResult<Unit> {
        return try {
            client.post(Routes.putWorkerPersonalData) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(personalProfile)
                )
            }
            authResult.authorised()
        } catch (e: Exception) {
            Log.d("Routes", "Failed to send worker personal profile at http request block")
            authResult.unauthorised()
        }
    }

    override suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit> {
        return try {
            client.post(Routes.putWorkerDriversLicence) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(licence)
                )
            }
            authResult.authorised()
        } catch (e: Exception) {
            Log.d("Routes", "Failed to send worker personal profile at http request block")
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
            val response = client.post(Routes.getWorkerSignupInfo) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(profileLoginAuthRequest))
            }
            dataStore.edit { settings ->
                settings[stringPreferencesKey(name = "JWT")] =
                    Json.decodeFromString<jwtTokinWithIsSupervisor>(response.body()).token
            }
            when (response.status) {
                HttpStatusCode.OK -> authResult.authorised(
                    Json.decodeFromString<jwtTokinWithIsSupervisor>(
                        response.body()
                    ).isSupervisor
                )
                HttpStatusCode.Conflict -> authResult.unauthorised()
                else -> authResult.unauthorised()
            }

        } catch (e: Exception) {
            Log.d("login", "catch block of get auth token http  request")
            authResult.unauthorised()
        }
    }

    override suspend fun getWorkerProfile(email: String): WorkerProfile {
        val jwt = read("JWT")
        return try {
            val response = client.post(Routes.getWorkerPersonalData) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(Email(email))
                )
                bearerAuth(jwt!!)
            }
            Json.decodeFromString(response.body())

        } catch (e: Exception) {
            workerProfileFail
        }
    }

    override suspend fun getWorkerDriversLicence(email: String): DriversLicence {
        val jwt = read("JWT")
        return try {
            val response = client.post(Routes.getWorkerDriversLicence) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(Email(email))
                )
                bearerAuth(jwt!!)
            }
            Json.decodeFromString(response.body())

        } catch (e: Exception) {
            licenceFail
        }
    }

    override suspend fun deleteAccount() {
        val jwt = read("JWT")
        try {
            val response = client.post(Routes.deleteWorkerAccount) {
                bearerAuth(jwt!!)
            }
        } catch (e: Exception) {
        }
    }

    //aws visualiser route functions for workers
// putSupervisorSignupInfo
// putSupervisorSiteInfo
// putSupervisorSpecialLicence
// putSupervisorWorked
// putSupervisorPersonalData
// putSupervisorExperience
    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile): authResult<Unit> {
        return try {
            client.post(Routes.putSupervisorPersonalData) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(supervisorProfile)
                )
            }
            authResult.authorised()
        } catch (e: Exception) {
            Log.d("Routes", "Failed to send worker personal profile at http request block")
            authResult.unauthorised()
        }
    }

    override suspend fun postSupervisorSite(site: SupervisorSite): authResult<Unit> {
        return try {
            client.post(Routes.putSupervisorSiteInfo) {
                contentType(ContentType.Application.Json)
                setBody(
                    Json.encodeToString(site)
                )
            }
            Log.d("main", "bottom of the post supervisor site call")
            authResult.authorised()
        } catch (e: Exception) {
            Log.d("Routes", "Failed to send supervisor site at http request block")
            authResult.unauthorised()
        }
    }

    //aws visualiser route functions for workers
// getSupervisorSignupInfo
// getSupervisorSiteInfo
// getSupervisorSpecialLicence
// getSupervisorWorked
// getSupervisorPersonalData
// getSupervisorExperience

    override suspend fun getSupervisorProfile(): SupervisorProfile {
        val jwt = read("JWT")
        return try {
            val response = client.get(Routes.getSupervisorPersonalData) {
                bearerAuth(jwt!!)
            }
            Json.decodeFromString(response.body())

        } catch (e: Exception) {
            supervisorProfileFail
        }
    }


    //get worker accounts for supervisor profile
    override suspend fun getListOfWorkerAccountsForSupervisor(): List<WorkerProfile> {
        return try {
            val response = client.get(Routes.getListOfWorkerAccounts)
            Log.d("json", response.body())
            return Json.decodeFromString(response.body())
        } catch (e: Exception) {
            Log.d("catch", "fuck")
            emptyList()
        }
    }
}

object Routes {
    private const val baseUrl = "https://tlc-nz.com/"

    //presign put request
    const val presignPutRequest = baseUrl + "s3PresignPut"

    //aws visualiser route functions for workers

    const val putWorkerSignupInfo = baseUrl + "putWorkerSignupInfo"

    //const val putWorkerSiteInfo = baseUrl + "putWorkerSiteInfo"
    //const val putWorkerSpecialLicence = baseUrl + "putWorkerSpecialLicence"
    //const val putDatesWorked = baseUrl + "putDatesWorked"
    const val putWorkerPersonalData = baseUrl + "putWorkerPersonalData"

    //const val putWorkerExperience = baseUrl + "putWorkerExperience"
    const val putWorkerDriversLicence = baseUrl + "putWorkerDriversLicence"

    //aws visualiser route functions for workers

    const val getWorkerSignupInfo = baseUrl + "getWorkerSignupAuth"

    //const val getWorkerSiteInfo = baseUrl + "getWorkerSiteInfo"
    //const val getWorkerSpecialLicence = baseUrl + "getWorkerSpecialLicence"
    //const val getDatesWorked = baseUrl + "getDatesWorked"
    const val getWorkerPersonalData = baseUrl + "getWorkerPersonalData"

    //const val getWorkerExperience = baseUrl + "getWorkerExperience"
    const val getWorkerDriversLicence = baseUrl + "getWorkerDriversLicence"

    //delete worker account
    const val deleteWorkerAccount = baseUrl + "deleteAccount"

    //aws visualiser route functions for Supervisors

    //const val putSupervisorSignupInfo = baseUrl + "putSupervisorSignupInfo"
    const val putSupervisorSiteInfo = baseUrl + "putSupervisorSiteInfo"
    const val putSupervisorPersonalData = baseUrl + "putSupervisorPersonalData"

    //aws visualiser route functions for Supervisors

    //const val getSupervisorSignupInfo = baseUrl + "getSupervisorSignupInfo"
    //const val getSupervisorSiteInfo = baseUrl + "getSupervisorSiteInfo"
    const val getSupervisorPersonalData = baseUrl + "getSupervisorPersonalData"
    const val getListOfWorkerAccounts = baseUrl + "getListOfWorkerAccounts"

}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}