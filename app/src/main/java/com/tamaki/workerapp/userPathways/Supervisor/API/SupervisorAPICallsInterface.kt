package com.tamaki.workerapp.userPathways.Supervisor.API

import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.SupervisorSite
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile

interface SupervisorAPICallsInterface {

    suspend fun postSupervisorProfile(supervisorProfile: SupervisorProfile): authResult<Unit>

    suspend fun postSupervisorSite(site: SupervisorSite): authResult<Unit>

    suspend fun getSupervisorProfile():SupervisorProfile

    suspend fun getListOfWorkerAccountsForSupervisor():List<WorkerProfile>

}