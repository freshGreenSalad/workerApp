package com.tamaki.workerapp.data.apiCallsToServer

import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.supervisorProfileFail
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

import javax.inject.Inject

class SupervisorAPICalls @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : AWSInterface {

    override suspend fun UploadphotoTos3Bucket(uri: Uri): String {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        val presign = preSignS3Urls(client, jwt).getPresignLink()
        putImageInS3bucketWithPresignedLink(uri, presign)
        return presign.split("?")[0]
    }

    private suspend fun putImageInS3bucketWithPresignedLink(
        uri: Uri,
        presign: String
    ) {
        try {
            uploadfileToS3(uri, presign)
        } catch (e: Exception) {
            Log.d("AWS", "Failed to add image to AWS")
        }
    }

    private suspend fun uploadfileToS3(uri: Uri, presign: String) {
        val file = File(uri.path!!)
        val bodydata = file.readBytes()
        client.put(presign) {
            contentType(ContentType.MultiPart.FormData)
            setBody(bodydata)
        }
    }

    override suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit> {
        return try {
            profileLoginAuthRequestValidation(profileLoginAuthRequest)
            var response: String = "HttpResponse"
            val json = errorHandelJsonEncodingOfString(profileLoginAuthRequest)
            try {
                response = postJsonToAPI(response, json)
            } catch (e:Exception){

            }
            DataStorePreferances(dataStore).edit(Json.decodeFromString<jwtTokinWithIsSupervisor>(response).token)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    private suspend fun postJsonToAPI(response: String, json: String): String {
        var response1 = response
        response1 = client.post(Routes.putWorkerSignupInfo) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }.body()
        return response1
    }

    private fun errorHandelJsonEncodingOfString(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor) =
        try {
            Json.encodeToString(
                profileLoginAuthRequest
            )
        } catch (e: Exception) {
            ""
        }

    private fun profileLoginAuthRequestValidation(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor) {
        if (!profileLoginAuthRequest.email.isEmailValid()) {
            throw Exception("email not valid")
        }
        if (profileLoginAuthRequest.password.length < 8) {
            throw Exception("password to small")
        }
    }

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

    override suspend fun deleteAccount() {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        try {
            val response = client.post(Routes.deleteWorkerAccount) {
                bearerAuth(jwt!!)
            }
        } catch (e: Exception) {
        }
    }

    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile): authResult<Unit> {
        val json = Json.encodeToString(supervisorProfile)
        Log.d("json",json)
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
        val json = Json.encodeToString(site)
        Log.d("",json)
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

    override suspend fun getSupervisorProfile(): SupervisorProfile {
        val jwt = DataStorePreferances(dataStore).read("JWT")
        return try {
            val response = client.get(Routes.getSupervisorPersonalData) {
                bearerAuth(jwt!!)
            }
            Json.decodeFromString(response.body())

        } catch (e: Exception) {
            supervisorProfileFail
        }
    }

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
    //server
    //private const val baseUrl = "https://tlc-nz.com/"
    //home
    //private const val baseUrl = "http://192.168.1.151:8080/"
    //nat and stus
    private const val baseUrl = "http://192.168.68.106:8080/"

    const val presignPutRequest = baseUrl + "s3PresignPut"

    const val putWorkerSignupInfo = baseUrl + "putWorkerSignupInfo"


    const val putWorkerPersonalData = baseUrl + "putWorkerPersonalData"

    const val putWorkerDriversLicence = baseUrl + "putWorkerDriversLicence"

    const val getWorkerSignupInfo = baseUrl + "getWorkerSignupAuth"

    const val getWorkerPersonalData = baseUrl + "getWorkerPersonalData"

    const val getWorkerDriversLicence = baseUrl + "getWorkerDriversLicence"

    const val deleteWorkerAccount = baseUrl + "deleteAccount"

    const val putSupervisorSiteInfo = baseUrl + "putSupervisorSiteInfo"

    const val putSupervisorPersonalData = baseUrl + "putSupervisorPersonalData"

    const val getSupervisorPersonalData = baseUrl + "getSupervisorPersonalData"

    const val getListOfWorkerAccounts = baseUrl + "getListOfWorkerAccounts"

}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}