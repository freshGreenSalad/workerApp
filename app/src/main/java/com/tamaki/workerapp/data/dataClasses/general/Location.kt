package com.tamaki.workerapp.data.dataClasses.general

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val Lat:Double,
    val Lng: Double
)
