package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.destinations.SupervisorProfilePageDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.destinations.AuthScreenDestination
import com.tamaki.workerapp.destinations.SupervisorHomeScaffoldDestination
import kotlinx.coroutines.Job
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainDrawer(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val scope = rememberCoroutineScope()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxWidth())
    {
        StandardPrimaryTextHeading("Menu")
        Divider(modifier = Modifier.fillMaxWidth().height(1.dp))
        StandardButton("Home", { navigator.navigate(SupervisorHomeScaffoldDestination)})
        StandardButton("Profile", { navigator.navigate(SupervisorProfilePageDestination)})
        StandardButton("Signout") { Logout(scope, viewModel, navigator, {scope.launch { viewState.drawerState.close()}}) }
        StandardButton("Delete Account") { DeleteAccount(scope, viewModel, {scope.launch { viewState.drawerState.close()}}, navigator) }
    }
}


fun Logout(
    scope: CoroutineScope,
    viewModel: SupervisorViewModel,
    navigator: DestinationsNavigator,
    closeDrawer: () -> Job
) {
    scope.launch {
        (viewModel::deleteAllFromDataStore)()
    }
    navigator.navigate(AuthScreenDestination)
    closeDrawer()
}

fun DeleteAccount(
    scope: CoroutineScope,
    viewModel: SupervisorViewModel,
    closeDrawer: () -> Job,
    navigator: DestinationsNavigator
) {
    scope.launch {
        (viewModel::deleteAccount)()
    }
    closeDrawer()
    navigator.navigate(AuthScreenDestination)
}