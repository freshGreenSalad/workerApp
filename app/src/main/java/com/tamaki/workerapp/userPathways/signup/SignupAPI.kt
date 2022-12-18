package com.tamaki.workerapp.userPathways.signup

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.apiCallsToServer.isEmailValid
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.EmailPasswordIsSupervisorPushId
import com.tamaki.workerapp.data.dataClasses.jwtTokinWithIsSupervisor
import com.tamaki.workerapp.data.utility.Routes
import com.tamaki.workerapp.data.utility.TryCatch
import com.tamaki.workerapp.data.utility.jsonclientfunctions
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SignupAPI @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
):SignupAPIInterface {

    override suspend fun UploadphotoTos3Bucket(uri: Uri): String {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        val presign = jsonclientfunctions().getPresignLink("profilePicture", jwt, client)
        TryCatch().suspendTryCatchWrapper { jsonclientfunctions().uploadfileToS3(client, uri, presign) }
        return presign.split("?")[0]
    }

    override suspend fun postProfileAuth(profileLoginAuthRequest: EmailPasswordIsSupervisorPushId): authResult<Unit> {
        //return try {
            emailPasswordStringValidation(profileLoginAuthRequest)
            val jsonProfileLoginAuthRequest = Json.encodeToString(profileLoginAuthRequest)
            val jWT = sendloginInformationReturnJWT(client, jsonProfileLoginAuthRequest, Routes.sendEmailPasswordGetJWT)
            DataStorePreferances(dataStore).edit("JWT", Json.decodeFromString<jwtTokinWithIsSupervisor>(jWT).token)
            DataStorePreferances(dataStore).edit("email", profileLoginAuthRequest.email)
            return authResult.authorised()
        //} catch (e: Exception) {
            //authResult.unauthorised()
        //}
    }

    override suspend fun postWorkerExperience(experience: Experience): authResult<Unit> {
        return try {
            val experienceJson = Json.encodeToString(experience)
            jsonclientfunctions().SendJsonViaRoute(
                client = client,
                json = experienceJson,
                Route = Routes.WorkerExperience
            )
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun postWorkerProfile(personalProfile: WorkerProfile): authResult<Unit> {
        return try {
            val personalProfileJson = Json.encodeToString(personalProfile)
            jsonclientfunctions().SendJsonViaRoute(
                client = client,
                json = personalProfileJson,
                Route = Routes.WorkerPersonalData
            )
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit> {
        return try {
            val licenceJson = Json.encodeToString(licence)
            jsonclientfunctions().SendJsonViaRoute(
                client = client,
                json = licenceJson,
                Route = Routes.WorkerDriversLicence
            )
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    private fun emailPasswordStringValidation(profileLoginAuthRequest: EmailPasswordIsSupervisorPushId) {
        if (!profileLoginAuthRequest.email.isEmailValid()) {
            throw Exception("email not valid")
        }
        if (profileLoginAuthRequest.password.length < 8) {
            throw Exception("password to small")
        }
    }

    private suspend fun sendloginInformationReturnJWT(client: HttpClient, json: String, Route:String): String {
        return client.post(Route) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }.body()
    }
}