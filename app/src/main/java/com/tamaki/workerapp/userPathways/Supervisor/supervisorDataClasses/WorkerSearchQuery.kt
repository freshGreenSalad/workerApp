package com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class WorkerSearchQuery(
    val experience: String,
    val lowerBound:Int,
    val upperBound:Int
)
