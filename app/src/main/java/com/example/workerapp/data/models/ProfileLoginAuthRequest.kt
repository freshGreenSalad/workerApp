package com.example.workerapp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ProfileLoginAuthRequest(
    val email:String,
    val password: String
)

