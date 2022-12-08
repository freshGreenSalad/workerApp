package com.tamaki.workerapp.userPathways.signup

import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.apiCallsToServer.isEmailValid
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequestWithIsSupervisor
import com.tamaki.workerapp.data.dataClasses.jwtTokinWithIsSupervisor
import com.tamaki.workerapp.data.utility.Routes
import com.tamaki.workerapp.data.utility.TryCatch
import com.tamaki.workerapp.data.utility.jsonclientfunctions
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

    override suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor): authResult<Unit> {
        return try {
            emailPasswordStringValidation(profileLoginAuthRequest)
            val jsonProfileLoginAuthRequest = Json.encodeToString(profileLoginAuthRequest)
            val jWT = sendloginInformationReturnJWT(client, jsonProfileLoginAuthRequest, Routes.sendEmailPasswordGetJWT)
            DataStorePreferances(dataStore).edit("JWT", Json.decodeFromString<jwtTokinWithIsSupervisor>(jWT).token)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    private fun emailPasswordStringValidation(profileLoginAuthRequest: ProfileLoginAuthRequestWithIsSupervisor) {
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