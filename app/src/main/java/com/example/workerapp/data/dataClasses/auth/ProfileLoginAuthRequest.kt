package com.example.workerapp.data.dataClasses.auth

import kotlinx.serialization.Serializable

@Serializable
data class ProfileLoginAuthRequest(
    val email:String,
    val password: String
)

