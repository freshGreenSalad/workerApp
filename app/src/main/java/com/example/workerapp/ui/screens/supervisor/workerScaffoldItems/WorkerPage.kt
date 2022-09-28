package com.example.workerapp.ui.screens.supervisor.workerScaffoldItems

import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.dataClasses.Worker
import com.example.workerapp.data.navgraphs.HomeViewNavGraph
import com.example.workerapp.ui.screens.supervisor.supervisorHome.SupervisorViewModel
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.MainDrawer
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.example.workerapp.ui.workerInfoPage.workerUITabs.WorkerPhotoTab
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph
@Destination
@Composable
fun WorkerPage(
    SelectedClickThroughWorker: Worker,
    viewModel: SupervisorViewModel,
    navigator: DestinationsNavigator,
) {
    val viewstate by viewModel.state.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val workerhistory = listOf<Pair<Int, String>>(
        Pair(2018, "quay street"),
        Pair(2018, "resedential"),
        Pair(2020, "build bridge"),
    )
    val workerSkill = listOf("formworker", "crane driver", "pie eater")
    val workerTools = listOf("drill", "ute", "hammer")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                navigator,
                viewModel::deleteAllFromDataStore,
                closeDrawer = {scope.launch { drawerState.close() }}
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {
                TopBar(
                    title = SelectedClickThroughWorker.name,
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            content = {
                WorkerPhotoTab(
                    navigator = navigator,
                    paddingValues = it,
                    worker = SelectedClickThroughWorker,
                    workerhistory = workerhistory,
                    workerTools = workerTools,
                    workerSkill = workerSkill,
                    addToWatchList = viewModel::addToWatchList,
                    ListOfSavedWorkers = viewstate.savedWorkers,
                    removeFromWatchlist = viewModel::removeFromWatchlist,
                )
            }
        )
    }
}