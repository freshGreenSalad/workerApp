package com.example.workerapp.Data.Room

import kotlinx.serialization.Serializable

@Serializable
data class Worker(
    val age: Int,
    val hourlyRate: Int,
    val imageURL: String,
    val key: Int,
    val name: String
)