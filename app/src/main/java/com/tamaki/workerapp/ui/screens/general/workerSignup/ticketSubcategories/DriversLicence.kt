package com.tamaki.workerapp.ui.screens.general.workerSignup.ticketSubcategories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.HighestClass
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.viewModel.TypeOfLicence

@Composable
fun DriversLicence(
    viewModel: SignupViewModel
) {
    val viewState by viewModel.stateTickets.collectAsState()
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
                LicenceRadioButton(TypeOfLicence.Empty, viewState.licenceType, {(viewModel::changeLicenceType)(TypeOfLicence.Empty)})
                LicenceRadioButton(TypeOfLicence.Learners, viewState.licenceType, {(viewModel::changeLicenceType)(TypeOfLicence.Learners)})
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                LicenceRadioButton(TypeOfLicence.Full, viewState.licenceType, {(viewModel::changeLicenceType)(TypeOfLicence.Full)})
                LicenceRadioButton(TypeOfLicence.Restricted, viewState.licenceType, {(viewModel::changeLicenceType)(TypeOfLicence.Restricted)})
            }
        }
        item {
            LicenceRow(viewState.licenceMap,"Wheels",viewModel::updateLicenceMap)
            LicenceRow(viewState.licenceMap,"Tracks",viewModel::updateLicenceMap)
            LicenceRow(viewState.licenceMap,"Rollers",viewModel::updateLicenceMap)
            LicenceRow(viewState.licenceMap,"Forks",viewModel::updateLicenceMap)
            LicenceRow(viewState.licenceMap,"HazardousGoods",viewModel::updateLicenceMap)
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
                        Text(viewState.highestClass.name)
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    ) {
                        LicenceDropDownMenuItem( {(viewModel::changeHighestClass)(HighestClass.Class1)},"Class 1")
                        LicenceDropDownMenuItem( {(viewModel::changeHighestClass)(HighestClass.Class2)},"Class 2")
                        LicenceDropDownMenuItem( {(viewModel::changeHighestClass)(HighestClass.Class3)},"Class 3")
                        LicenceDropDownMenuItem( {(viewModel::changeHighestClass)(HighestClass.Class4)},"Class 4")
                        LicenceDropDownMenuItem( {(viewModel::changeHighestClass)(HighestClass.Class5)},"Class 5")

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenceRadioButton(selectedTypeOfLicence:TypeOfLicence, representedTypeOfLicence: TypeOfLicence, changeLicenceType:()->Unit){
    Text(
        text = selectedTypeOfLicence.name,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
    RadioButton(
        selected = selectedTypeOfLicence == representedTypeOfLicence,
        onClick = { changeLicenceType() })
}

@Composable
fun LicenceDropDownMenuItem(updateHighestClass:()->Unit, name:String){
    DropdownMenuItem(
        text = { Text(name) },
        onClick = { updateHighestClass() },
        modifier = Modifier.width(280.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicenceRow(licenceMap:Map<String, Boolean>, text:String, updateLicenceEntry:(String)->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        licenceMap[text]?.let {
            Checkbox(
                checked = it,
                onCheckedChange = { updateLicenceEntry(text) })
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}