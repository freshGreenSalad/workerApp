package com.tamaki.workerapp.data.dataClasses.auth

import kotlinx.serialization.Serializable

@Serializable
data class jwtTokin(
    val token: String
)
