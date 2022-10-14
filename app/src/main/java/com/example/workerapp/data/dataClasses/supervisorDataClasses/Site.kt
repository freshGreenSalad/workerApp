package com.example.workerapp.data.dataClasses

import com.example.workerapp.data.dataClasses.general.Location
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class SupervisorSite(
    val email:String,
    val address: String,
    @Contextual
    val location: Location
)
