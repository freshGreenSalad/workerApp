package com.example.workerapp.ui.screens.general.profileCreation.employee

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.dataClasses.Licence
import com.example.workerapp.ui.screens.general.profileCreation.*

@Composable
fun Employee(
    listoflicences: List<String>,
    addSpecilizedLicence: (String) -> Unit,
    removeSpecilisedLicence: (String) -> Unit,
    addExperience: (String) -> Unit,
    removeExperience: (String) -> Unit,
    experienceList: List<String>,
    licence: Licence,
    UpdateLicencefullLicence: (String) -> Unit,
    updateFirstName:(String)->Unit,
    updateLastName:(String)->Unit,
) {
    var showBasicInformation by remember { mutableStateOf(false) }
    var showlicence by remember { mutableStateOf(false) }
    var showspecilizedMachineryLicence by remember { mutableStateOf(false) }
    var showExperiance by remember { mutableStateOf(false) }
    var showtools by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn() {
            item {
                switchrow(
                    "Basic Information",
                    showBasicInformation,
                    { showBasicInformation = !showBasicInformation })
                if (showBasicInformation) {
                    basicInformationEmployee(updateFirstName,updateLastName)
                }
                switchrow("Drivers Licence", showlicence, { showlicence = !showlicence })
                if (showlicence) {
                    licence(
                        licence,
                        UpdateLicencefullLicence
                    )
                }
                switchrow(
                    "Specilised Licences",
                    showspecilizedMachineryLicence,
                    { showspecilizedMachineryLicence = !showspecilizedMachineryLicence })
                if (showspecilizedMachineryLicence) {
                    specializedLicences(
                        listoflicences,
                        addSpecilizedLicence,
                        removeSpecilisedLicence
                    )
                }
                switchrow("Experience", showExperiance, { showExperiance = !showExperiance })
                if (showExperiance) {
                    experience(
                        addExperience,
                        removeExperience,
                        experienceList
                    )
                }
                switchrow("Tools", showtools, { showtools = !showtools })
                if (showtools) {
                    tools()
                }
            }
        }
    }
}

@Composable
fun switchrow(
    name: String,
    boolean: Boolean,
    clicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(5.dp)
                    .alpha(.5f),
                style = MaterialTheme.typography.headlineSmall
            )
            Switch(checked = boolean,
                onCheckedChange = {
                    clicked()
                },
                thumbContent = { switchIcon(boolean) }
            )
        }
        Divider(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun switchIcon(
    showIcon: Boolean
) {
    if (showIcon) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = null,
            modifier = Modifier.size(SwitchDefaults.IconSize),
        )
    } else {
        null
    }
}
