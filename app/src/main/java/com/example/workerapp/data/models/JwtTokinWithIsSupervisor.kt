package com.example.workerapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class jwtTokinWithIsSupervisor(
    val token: String,
    val isSupervisor: Boolean
)