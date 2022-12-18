package com.tamaki.workerapp.data.dataClasses.auth

import kotlinx.serialization.Serializable

@Serializable
data class EmailPasswordIsSupervisorPushId(
    val email:String,
    val password: String,
    val isSupervisor: Boolean,
    val pushId:String
)
