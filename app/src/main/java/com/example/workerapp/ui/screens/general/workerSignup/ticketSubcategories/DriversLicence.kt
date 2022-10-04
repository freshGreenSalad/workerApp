package com.example.workerapp.ui.screens.general.workerSignup.ticketSubcategories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.viewModel.HighestClass
import com.example.workerapp.data.viewModel.TypeOfLicence

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversLicence(
    LicenceType: TypeOfLicence,
    ChangeLicenceType: (TypeOfLicence) -> Unit,
    licenceMap: Map<String, Boolean>,
    updateLicenceEntry: (String) -> Unit,
    highestClass: HighestClass,
    UpdateHighestClass: (HighestClass) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "No Licence",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                RadioButton(
                    selected = LicenceType == TypeOfLicence.Empty,
                    onClick = { ChangeLicenceType(TypeOfLicence.Empty) })
                Text(
                    text = "Learners",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                RadioButton(
                    selected = LicenceType == TypeOfLicence.Learners,
                    onClick = { ChangeLicenceType(TypeOfLicence.Learners) })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Restricted",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                RadioButton(
                    selected = LicenceType == TypeOfLicence.Restricted,
                    onClick = { ChangeLicenceType(TypeOfLicence.Restricted) })
                Text(
                    text = "Full",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                RadioButton(
                    selected = LicenceType == TypeOfLicence.Full,
                    onClick = { ChangeLicenceType(TypeOfLicence.Full) })
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["forks"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateLicenceEntry("forks") })
                }
                Text(
                    text = "Forks",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["wheels"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateLicenceEntry("wheels") })
                }
                Text(
                    text = "Wheels",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["rollers"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateLicenceEntry("rollers") })
                }
                Text(
                    text = "Rollers",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["dangerousGoods"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateLicenceEntry("dangerousGoods") })
                }
                Text(
                    text = "Dangerous Goods",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                licenceMap["tracks"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateLicenceEntry("tracks") })
                }
                Text(
                    text = "Tracks",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
                    
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                    
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(highestClass.toString())
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },

                    ) {
                    DropdownMenuItem(
                        text = { Text("Class 1") },
                        onClick = { UpdateHighestClass(HighestClass.Class1) },
                        modifier = Modifier.width(280.dp)
                    )
                    DropdownMenuItem(
                        text = { Text("Class 2") },
                        onClick = { UpdateHighestClass(HighestClass.Class2) },
                        modifier = Modifier.width(280.dp)
                    )
                    DropdownMenuItem(
                        text = { Text("Class 3") },
                        onClick = { UpdateHighestClass(HighestClass.Class3) },
                        modifier = Modifier.width(280.dp)
                    )
                    DropdownMenuItem(
                        text = { Text("Class 4") },
                        onClick = { UpdateHighestClass(HighestClass.Class4) },
                        modifier = Modifier.width(280.dp)
                    )
                    DropdownMenuItem(
                        text = { Text("Class 5") },
                        onClick = { UpdateHighestClass(HighestClass.Class5) },
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
        }
    }
}