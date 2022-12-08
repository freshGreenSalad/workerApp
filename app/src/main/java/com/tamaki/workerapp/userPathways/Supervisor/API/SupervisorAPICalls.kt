package com.tamaki.workerapp.userPathways.Supervisor.API

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.tamaki.workerapp.data.DataStorePreferances
import com.tamaki.workerapp.data.utility.Routes
import com.tamaki.workerapp.data.utility.jsonclientfunctions
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.SupervisorSite
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.supervisorProfileFail
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SupervisorAPICalls @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) : SupervisorAPICallsInterface {

    override suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile): authResult<Unit> {
        return try {
            val supervisorProfileJson = Json.encodeToString(supervisorProfile)
            jsonclientfunctions().SendJsonViaRoute(client, supervisorProfileJson, Routes.SupervisorPersonalData)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun postSupervisorSite(site: SupervisorSite): authResult<Unit> {
        return try {
            val siteJson = Json.encodeToString(site)
            jsonclientfunctions().SendJsonViaRoute(client, siteJson, Routes.SupervisorSiteInfo)
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }

    override suspend fun getSupervisorProfile(): SupervisorProfile {
        val jwt = DataStorePreferances(dataStore).read("JWT")!!
        return try {
            val response = client.get(Routes.SupervisorPersonalData) { bearerAuth(jwt) }
            Json.decodeFromString(response.body())
        } catch (e: Exception) {
            supervisorProfileFail
        }
    }

    override suspend fun getListOfWorkerAccountsForSupervisor(): List<WorkerProfile> {
        return try {
            val response = client.get(Routes.getListOfWorkerAccounts)
            return Json.decodeFromString(response.body())
        } catch (e: Exception) {
            emptyList()
        }
    }
}