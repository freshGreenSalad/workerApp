package com.tamaki.workerapp.userPathways.signup.workerSignup.ticketSubcategories

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
import com.tamaki.workerapp.data.viewModel.SignupState
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.viewModel.TypeOfLicence
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.ui.components.StandardSpacer

@Composable
fun DriversLicence(
    viewModel: SignupViewModel
) {
    val State by viewModel.stateLogin.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            StandardPrimaryTextHeading("Drivers Licence")
            StandardSpacer()
            RadioButtonsOfLicenceType(State, viewModel)
            StandardSpacer()
            CheckboxesOfEndrsments(State, viewModel)
            StandardSpacer()
            DropDownMenuOfLicenceClass(expanded, State, viewModel)
        }
    }
}

@Composable
private fun DropDownMenuOfLicenceClass(
    expanded: Boolean,
    State: SignupState,
    viewModel: SignupViewModel
) {
    var expanded1 = expanded
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center

    ) {
        OutlinedButton(
            onClick = { expanded1 = true },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth(),

            ) {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Text(State.highestClass.name)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
            }
        }
        DropDownMenuItems(expanded1, viewModel)
    }
}

@Composable
private fun RadioButtonsOfLicenceType(
    State: SignupState,
    viewModel: SignupViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        LicenceRadioButton(
            TypeOfLicence.Empty,
            State.licenceType,
            { (viewModel::changeLicenceType)(TypeOfLicence.Empty) })
        LicenceRadioButton(
            TypeOfLicence.Learners,
            State.licenceType,
            { (viewModel::changeLicenceType)(TypeOfLicence.Learners) })
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        LicenceRadioButton(
            TypeOfLicence.Full,
            State.licenceType
        ) { (viewModel::changeLicenceType)(TypeOfLicence.Full) }
        LicenceRadioButton(
            TypeOfLicence.Restricted,
            State.licenceType
        ) { (viewModel::changeLicenceType)(TypeOfLicence.Restricted) }
    }
}

@Composable
fun CheckboxesOfEndrsments(
    State: SignupState,
    viewModel: SignupViewModel
) {
    LicenceRow(State.forks, "Forks", viewModel::UpdateForks)
    StandardSpacer()
    LicenceRow(State.tracks, "Tracks", viewModel::UpdateTracks)
    StandardSpacer()
    LicenceRow(State.rollers, "Rollers", viewModel::UpdateRollers)
    StandardSpacer()
    LicenceRow(State.wheels, "Wheels", viewModel::UpdateWheels)
    StandardSpacer()
    LicenceRow(State.dangerousGoods, "HazardousGoods", viewModel::UpdateDangerousGoods)
}

@Composable
private fun DropDownMenuItems(
    expanded: Boolean,
    viewModel: SignupViewModel
) {
    var expanded1 = expanded
    DropdownMenu(
        expanded = expanded1,
        onDismissRequest = { expanded1 = false },
    ) {
        LicenceDropDownMenuItem({ (viewModel::changeHighestClass)(HighestClass.Class1) }, "Class 1")
        LicenceDropDownMenuItem({ (viewModel::changeHighestClass)(HighestClass.Class2) }, "Class 2")
        LicenceDropDownMenuItem({ (viewModel::changeHighestClass)(HighestClass.Class3) }, "Class 3")
        LicenceDropDownMenuItem({ (viewModel::changeHighestClass)(HighestClass.Class4) }, "Class 4")
        LicenceDropDownMenuItem({ (viewModel::changeHighestClass)(HighestClass.Class5) }, "Class 5")

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
private fun LicenceRow(hasEndoursment:Boolean, text:String, updateLicenceEntry:()->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = hasEndoursment,
            onCheckedChange = { updateLicenceEntry() }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}