package com.tamaki.workerapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
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
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@OptIn(ExperimentalMaterial3Api::class)
@WorkerNavGraph(start = true)
@Destination
@Composable
fun WorkerProfile(
    viewmodel: WorkerViewModel,
    navigator: DestinationsNavigator
) {
    val viewState by viewmodel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        workerProfileScaffold(
            drawerState = viewState.drawerState,
            navigator = navigator,
            deleteFromDataStore = viewmodel::deleteAllFromDataStore,
            deleteAccount = viewmodel::deleteAccount
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun workerProfileScaffold (
    drawerState: DrawerState,
    navigator: DestinationsNavigator,
    deleteFromDataStore: KSuspendFunction0< Unit>,
    deleteAccount: KSuspendFunction0< Unit>
){
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            WorkerDrawer(
                navigator,
                deleteFromDataStore,
                closeDrawer = {scope.launch { drawerState.close() }},
                deleteAccount = deleteAccount
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
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {},
            content = {
                Box(){
                    WorkerHomeProfile(it, navigator)
                }
            }
        )
    }
}