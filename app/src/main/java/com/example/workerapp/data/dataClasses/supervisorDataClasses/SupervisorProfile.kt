package com.example.workerapp.data.dataClasses.supervisorDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class SupervisorProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val personalPhoto: String,
)

val supervisorProfileFail = SupervisorProfile("","","","")