package com.example.workerapp.data.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class jwtTokinWithIsSupervisor(
    val token: String,
    val isSupervisor: Boolean
)