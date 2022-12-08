package com.tamaki.workerapp.userPathways.Supervisor.UI.workerScaffoldItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.ui.workerInfoPage.workerUITabs.WorkerPhotoTab
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
            topBar = {
                TopBar(
                    title = SelectedClickThroughWorker.firstName,
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            floatingActionButton = {

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        .clickable {
                            //navigator.navigate(HireScafoldDestination(worker = worker))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Hire ${SelectedClickThroughWorker.firstName}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
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

