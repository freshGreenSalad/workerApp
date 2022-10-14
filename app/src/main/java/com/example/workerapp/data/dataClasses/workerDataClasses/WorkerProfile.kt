package com.example.workerapp.data.dataClasses.workerDataClasses

import com.example.workerapp.data.viewModel.HighestClass
import com.example.workerapp.data.viewModel.TypeOfLicence
import kotlinx.serialization.Serializable

@Serializable
data class WorkerProfile(
    val email: String,
    val firstName: String,
    val lastName: String,
    val personalPhoto: String,
    val rate: Int
)

val licenceFail = DriversLicence(TypeOfLicence.Empty, mapOf("" to false), HighestClass.Class1)
val workerProfileFail = WorkerProfile("","","","",0)
