package com.example.workerapp.ui.screens.worker.workerProfile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.workerapp.data.viewModel.WorkerViewModel
import com.example.workerapp.data.navgraphs.WorkerNavGraph
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@WorkerNavGraph
@Composable
@Destination
fun WorkerProfilePageComposable(
    navigator: DestinationsNavigator,
    viewModel: WorkerViewModel,
) {
    val viewState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = viewState.drawerState
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            WorkerDrawer(
                navigator = navigator,
                deleteFromDataStore = viewModel::delete,
                closeDrawer = {scope.launch { drawerState.close() }}
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Profile",
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it))
        }
    }
}