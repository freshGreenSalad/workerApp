package com.tamaki.workerapp.data.dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class JobSite(
    val name:String,
    val address:String,
    val startdate:String,
    val supervisor:String,
)
