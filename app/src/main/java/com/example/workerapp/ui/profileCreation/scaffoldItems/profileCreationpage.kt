package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.models.Licence
import com.example.workerapp.data.viewModel.EmployeerOrEmployee
import com.example.workerapp.data.viewModel.signUpViewModel
import com.example.workerapp.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.ui.profileCreation.employee.Employee
import com.example.workerapp.ui.profileCreation.scaffoldItems.TopBarProfileCreationPage
import com.example.workerapp.ui.profileCreation.scaffoldItems.bottomAppBarComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph(start = true)
@Destination
@Composable
fun ProfileCreationPage(
    viewModel: signUpViewModel,
    navigator: DestinationsNavigator
) {
    val viewState by viewModel.state.collectAsState()
    val selectedtab = viewState.selectedEmployerOrEmployee
    ProfileCreation(
        navigator,
        selectedtab = selectedtab,
        onClick = viewModel::changeUserType,
        listoflicences = viewState.listOfLicences,
        viewModel::addSpecilizedLicence,
        viewModel::removeFromSpecilisedLicence,
        viewModel::addExperience,
        viewModel::removeFromExperience,
        viewState.experience,
        viewState.licence,
        viewModel::updateLicencefullLicence
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileCreation(
    navigator: DestinationsNavigator,
    selectedtab: EmployeerOrEmployee,
    onClick: (String) -> Unit,
    listoflicences: List<String>,
    addSpecilizedLicence: (String) -> Unit,
    removeSpecilisedLicence: (String) -> Unit,
    addExperience: (String) -> Unit,
    removeExperience: (String) -> Unit,
    experienceList: List<String>,
    licence: Licence,
    UpdateLicencefullLicence: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {Scaffold(
        topBar = {TopBarProfileCreationPage("Create your profile")},
        bottomBar = { bottomAppBarComposable(
            selectedtab,
            navigator = navigator
        ) },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Row() {
                    radiobuttonGroup(onClick, selectedtab)
                }

                Surface(modifier = Modifier.fillMaxSize()) {
                    when (selectedtab) {
                        EmployeerOrEmployee.Employee -> {
                            Employee(
                                listoflicences,
                                addSpecilizedLicence,
                                removeSpecilisedLicence,
                                addExperience,
                                removeExperience,
                                experienceList,
                                licence,
                                UpdateLicencefullLicence
                            )
                        }
                        EmployeerOrEmployee.Employer -> {
                            Employer()
                        }
                    }
                }
            }
        }
    )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun radiobuttonGroup(
    onOptionSelected: (String)->Unit,
    selectedtab: EmployeerOrEmployee
) {
    Row(Modifier.selectableGroup()) {
        Row(
            Modifier
                .width(200.dp)
                .height(56.dp)
                .selectable(
                    selected = ("Employer" == selectedtab.toString()),
                    onClick = { onOptionSelected("Employer") },
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = ("Employer" == selectedtab.toString()),
                onClick = null
            )
            Text(
                text = "Employer",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Row(
            Modifier
                .width(200.dp)
                .height(56.dp)
                .selectable(
                    selected = ("Employee" == selectedtab.toString()),
                    onClick = { onOptionSelected("Employee") },
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = ("Employee" == selectedtab.toString()),
                onClick = null
            )
            Text(
                text = "Employee",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}





























