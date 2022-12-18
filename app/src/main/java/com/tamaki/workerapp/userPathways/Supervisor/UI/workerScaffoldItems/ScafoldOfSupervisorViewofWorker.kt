package com.tamaki.workerapp.userPathways.Supervisor.UI.workerScaffoldItems

import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.ui.workerInfoPage.workerUITabs.WorkerPhotoTab
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.destinations.HireScafoldDestination
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph
@Destination
@Composable
fun WorkerPage(
    SelectedClickThroughWorker: WorkerProfile,
    viewModel: SupervisorViewModel,
    navigator: DestinationsNavigator,
) {
    val state = viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                navigator,
                viewModel
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = { TopBar(title = SelectedClickThroughWorker.firstName, openDrawer = { scope.launch { drawerState.open() } }) },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {
                if(SelectedClickThroughWorker in state.value.hiredWorkerList){

                }else {
                    StandardButton(text = "Hire ${SelectedClickThroughWorker.firstName}") {
                        navigator.navigate(HireScafoldDestination(worker = SelectedClickThroughWorker))
                    }
                }
            },
            content = {
                WorkerPhotoTab(
                    paddingValues = it,
                    worker = SelectedClickThroughWorker,
                    getWorkerDriverLicenceviewModel = viewModel::getWorkerDriversLicence
                )
            },
        )
    }
}

