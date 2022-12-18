package com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class WorkerDate(
    val day:Int,
    val month:Int,
    val year:Int
)
