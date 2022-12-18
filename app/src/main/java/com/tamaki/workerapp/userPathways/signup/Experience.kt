package com.tamaki.workerapp.userPathways.signup

@kotlinx.serialization.Serializable
data class Experience(
    val experience: String,
    val years:Int,
    val email: String,
)
