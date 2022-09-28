package com.example.workerapp.data.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class ProfileInformation(
    val firstname:String,
    val lastname:String,
    val supervisor:Boolean,
    val experience: List<String>,
    val licences: List<String>,
    val driversLicence: Licence
)

