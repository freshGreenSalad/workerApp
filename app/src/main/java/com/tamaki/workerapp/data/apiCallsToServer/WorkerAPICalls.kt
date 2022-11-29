package com.tamaki.workerapp.data.apiCallsToServer

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.Email
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.DriversLicence
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.licenceFail
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.workerProfileFail
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject


class WorkerAPICalls @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : WorkerAPICallInterface {

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

    override suspend fun getWorkerProfile(email: String): WorkerProfile {
        val jwt = DataStorePreferances( dataStore).read("JWT")
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
        val jwt = DataStorePreferances(dataStore).read("JWT")
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
}