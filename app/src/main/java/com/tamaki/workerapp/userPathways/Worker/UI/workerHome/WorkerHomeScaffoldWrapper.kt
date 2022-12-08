package com.tamaki.workerapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.WorkerViewModel
import com.tamaki.workerapp.data.navgraphs.WorkerNavGraph
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.userPathways.Worker.UI.workerProfile.WorkerDrawer
import com.tamaki.workerapp.userPathways.Worker.UI.workerProfile.WorkerHomeProfile
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.FillMaxSizePaddingBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@WorkerNavGraph(start = true)
@Destination
@Composable
fun WorkerProfile(
    viewModel: WorkerViewModel,
    navigator: DestinationsNavigator
) {
    val viewState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = viewState.drawerState,
        drawerContent = {
            WorkerDrawer(
                navigator,
                viewModel::deleteAllFromDataStore,
                closeDrawer = {scope.launch { viewState.drawerState.close() }},
                deleteAccount = viewModel::deleteAccount
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {
                TopBar(
                    title = "Home",
                    openDrawer = {
                        scope.launch {
                            viewState.drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {},
            content = { FillMaxSizePaddingBox(it) { WorkerHomeProfile(navigator)} }
        )
    }
}