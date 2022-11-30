package com.tamaki.workerapp.userPathways.Worker.API

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.utility.Routes
import com.tamaki.workerapp.data.utility.jsonclientfunctions
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.DriversLicence
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.licenceFail
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.workerProfileFail
import io.ktor.client.*
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
            val personalProfileJson = Json.encodeToString(personalProfile)
            jsonclientfunctions().SendJsonViaRoute(client, personalProfileJson, Routes.putWorkerPersonalData)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun postWorkerDriversLicence(licence: DriversLicence): authResult<Unit> {
        return try {
            val licenceJson = Json.encodeToString(licence)
            jsonclientfunctions().SendJsonViaRoute(client, licenceJson, Routes.putWorkerDriversLicence)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun getWorkerProfile(email: String): WorkerProfile {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        return try{
            val workerProfileJson = jsonclientfunctions().getHttpResponseWithRawEmail(client,email, jwt, Routes.getWorkerPersonalData)
            Json.decodeFromString(workerProfileJson)
        } catch (e: Exception) {
            workerProfileFail
        }
    }

    override suspend fun getWorkerDriversLicence(email: String): DriversLicence {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        return try {
            val driversLicenceJason = jsonclientfunctions().getHttpResponseWithRawEmail(client,email, jwt, Routes.getWorkerDriversLicence)
            Json.decodeFromString(driversLicenceJason)
        } catch (e: Exception) {
            licenceFail
        }
    }
}