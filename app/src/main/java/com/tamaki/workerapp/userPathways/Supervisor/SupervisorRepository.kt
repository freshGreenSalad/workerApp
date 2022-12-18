package com.tamaki.workerapp.userPathways.Supervisor

import com.tamaki.workerapp.userPathways.Supervisor.API.SupervisorAPICalls
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SuccessStatus
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.WorkerDate
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.WorkerSearchQuery
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import javax.inject.Inject

class SupervisorRepository @Inject constructor(
    private var SupervisorEndpoint: SupervisorAPICalls
){
    suspend fun hireWorker(workerEmail:String, date:WorkerDate):SuccessStatus = SupervisorEndpoint.hireWorker(workerEmail, date)

    suspend fun getListOfHiredWorkers():List<WorkerProfile> = SupervisorEndpoint.getlistofHiredWorkers()

    suspend fun searchForWorker(workerSearchQuery: WorkerSearchQuery):List<WorkerProfile> = SupervisorEndpoint.SearchForWorkers(workerSearchQuery)
}
