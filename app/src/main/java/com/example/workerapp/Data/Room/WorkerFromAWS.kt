package com.example.workerapp.Data.Room

import kotlinx.serialization.Serializable


@Serializable
data class WorkerFromAWS(
    val key: Int,
    val name: String,
    val age: Int,
    val hourlyRate: Int,
) : java.io.Serializable
