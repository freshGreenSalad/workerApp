package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.models.Licence

@Composable
fun licence(
    licence: Licence,
    UpdateLicencefullLicence: (String)-> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(50.dp,0.dp,50.dp,0.dp)) {
        switchrow("Learners Licence",licence.learners,{UpdateLicencefullLicence("learners")})
        switchrow("Restricted Licence",licence.restricted,{UpdateLicencefullLicence("restricted")})
        switchrow("Full Licence",licence.fullLicence,{UpdateLicencefullLicence("fullLicence")})
        if (licence.fullLicence) {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "endorsments"
            )
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
            switchrow("wheels",licence.wheels,{UpdateLicencefullLicence("wheels")})
            switchrow("tracks",licence.tracks,{UpdateLicencefullLicence("tracks")})
            switchrow("rollers",licence.rollers,{UpdateLicencefullLicence("rollers")})
            switchrow("forks",licence.forks,{UpdateLicencefullLicence("forks")})
            switchrow("hazardas goods",licence.hazardus,{UpdateLicencefullLicence("hazardus")})
            switchrow("class 2",licence.class2,{UpdateLicencefullLicence("class2")})
            switchrow("class 3",licence.class3,{UpdateLicencefullLicence("class3")})
            switchrow("class 4",licence.class4,{UpdateLicencefullLicence("class4")})
            switchrow("class 5",licence.class5,{UpdateLicencefullLicence("class5")})
        }
    }
}
