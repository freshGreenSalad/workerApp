package com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class HireWorker(
    val supervisorEmail: String,
    val WorkerEmail:String,
    val Date:WorkerDate
)