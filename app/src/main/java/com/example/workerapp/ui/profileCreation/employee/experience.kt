package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun experience(
    addExperience: (String)-> Unit,
    removeExperience: (String)-> Unit,
    experienceList: List<String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
        ) {
        DropDownMenuexperience(
            addExperience,
        removeExperience,
        experienceList
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuexperience(
    addExperience: (String)-> Unit,
    removeExperience: (String)-> Unit,
    experienceList: List<String>
) {
    val optionsExperience = listOf(
        "formworker",
        "digger driver",
        "steelie",
        "foreman",
        "supervisor"
    )
    val optionsExperienceYears = listOf(
        "1",
        "2",
        "3",
        "4",
        "5"
    )
    var expanded by remember { mutableStateOf(false) }
    var expandedyears by remember { mutableStateOf(false) }
    var selectedOptionsExperience by remember { mutableStateOf(optionsExperience[0]) }
    var selectedExperienceYears by remember { mutableStateOf(optionsExperienceYears[0]) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionsExperience,
                    onValueChange = {},
                    label = { Text("Label") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    optionsExperience.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.toString()) },
                            onClick = {
                                selectedOptionsExperience = selectionOption.toString()
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                ExposedDropdownMenuBox(
                    expanded = expandedyears,
                    onExpandedChange = { expandedyears = !expandedyears },
                ) {
                    TextField(
                        readOnly = true,
                        value = selectedExperienceYears,
                        onValueChange = {},
                        label = { Text("Label") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedyears) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    )
                    ExposedDropdownMenu(
                        expanded = expandedyears,
                        onDismissRequest = { expandedyears = false },
                    ) {
                        optionsExperienceYears.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    selectedExperienceYears = selectionOption
                                    expandedyears = false
                                }
                            )
                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            addExperience("$selectedOptionsExperience $selectedExperienceYears years")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                        //    modifier = Modifier.size(SwitchDefaults.IconSize)
                    )

                }
            }
        }
        specilisedLicenceChipGroup(
            experienceList,
            removeExperience
        )
    }
}