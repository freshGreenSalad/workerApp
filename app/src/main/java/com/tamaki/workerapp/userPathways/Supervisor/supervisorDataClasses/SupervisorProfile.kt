package com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses

import kotlinx.serialization.Serializable

@Serializable
data class SupervisorProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val personalPhoto: String,
)

val supervisorProfileFail = SupervisorProfile("","","","")