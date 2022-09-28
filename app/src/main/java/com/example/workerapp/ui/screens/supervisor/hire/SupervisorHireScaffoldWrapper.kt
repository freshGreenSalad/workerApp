package com.example.workerapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.dataClasses.Worker
import com.example.workerapp.data.navgraphs.HomeViewNavGraph
import com.example.workerapp.ui.screens.supervisor.supervisorHome.MainViewModel
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.MainDrawer
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph
@Destination
@Composable
fun HireScafold(
    worker: Worker,
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
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
                    title = "hire ${worker.name}",
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            content = {
                HireWorker(
                    paddingValues = it,
                    worker = worker
                )
            }
        )
    }
}