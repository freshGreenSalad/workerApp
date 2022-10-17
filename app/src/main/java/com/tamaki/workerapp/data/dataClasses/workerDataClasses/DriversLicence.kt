package com.tamaki.workerapp.data.dataClasses.workerDataClasses

import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.TypeOfLicence
import kotlinx.serialization.Serializable

@Serializable
data class DriversLicence(
    val typeOfLicence: TypeOfLicence,
    val licenceMap: Map<String,Boolean>,
    val highestClass: HighestClass
)
