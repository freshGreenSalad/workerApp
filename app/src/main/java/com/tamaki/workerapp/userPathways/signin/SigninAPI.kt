package com.tamaki.workerapp.data.apiCallsToServer

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.*
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.data.utility.Routes
import com.tamaki.workerapp.data.utility.TryCatch
import com.tamaki.workerapp.data.utility.jsonclientfunctions
import com.tamaki.workerapp.userPathways.signin.signinAPIInterface
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject

class LoginAPICalls @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : signinAPIInterface {

    override suspend fun Login(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Boolean?> {
        return try {
            val profileLoginAuthRequestJson =  Json.encodeToString(profileLoginAuthRequest)
            val response = jsonclientfunctions().SendJsonViaRouteReturnBody(client,profileLoginAuthRequestJson , Routes.sendEmailPasswordGetJWT)
            val token = Json.decodeFromString<jwtTokinWithIsSupervisor>(response.body()).token
            DataStorePreferances(dataStore).edit("JWT", token)
            CheckAuthorisationResultAndSupervisorStatus(response)
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun deleteAccount() {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        TryCatch().suspendTryCatchWrapper { client.post(Routes.deleteWorkerAccount) { bearerAuth(jwt) } }
    }

    suspend fun CheckAuthorisationResultAndSupervisorStatus(response: HttpResponse): authResult<Boolean?> =
        when (response.status) {
            HttpStatusCode.OK -> CheckIsSupervisor(response)
            HttpStatusCode.Conflict -> authResult.unauthorised()
            else -> authResult.unauthorised()
        }

    suspend fun CheckIsSupervisor(response: HttpResponse): authResult.authorised<Boolean?> =
        authResult.authorised(
            Json.decodeFromString<jwtTokinWithIsSupervisor>(
                response.body()
            ).isSupervisor
        )
}