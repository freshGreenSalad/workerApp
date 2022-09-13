package com.example.workerapp.data.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable

@Serializable
data class Licence(
    var fullLicence: Boolean,
    var learners: Boolean,
    var restricted: Boolean,
    var wheels: Boolean,
    var tracks: Boolean,
    var rollers: Boolean,
    var  forks: Boolean,
    var  hazardus: Boolean,
    var  class2: Boolean,
    var  class3: Boolean,
    var  class4: Boolean,
    var  class5: Boolean,
)
