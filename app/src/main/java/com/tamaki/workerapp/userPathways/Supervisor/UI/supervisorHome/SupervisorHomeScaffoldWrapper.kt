package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.BottomAppBarHomePage
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs.SupervisorHome
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs.WorkerSearch
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph(start = true)
@Destination
@Composable
fun SupervisorHomeScaffold(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val viewState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    Surface {
        ModalNavigationDrawer(
            drawerState = viewState.drawerState,
            drawerContent = {
                MainDrawer(
                    navigator,
                    viewModel,
                )
            }
        ) {
            Scaffold(
                modifier = Modifier.padding(.4.dp),
                topBar = {
                    TopBar(
                        title = viewState.selectedHomeBarTab.name,
                        openDrawer = {
                            scope.launch {
                                viewState.drawerState.open()
                            }
                        }
                    )
                },
                bottomBar = { BottomAppBarHomePage(viewModel) },
                content = {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(it)
                    ) {
                        when (viewState.selectedHomeBarTab) {
                            HomeBottomAppBarTabs.Home -> {
                                SupervisorHome(
                                    navigator = navigator,
                                    viewModel = viewModel
                                )
                            }
                            HomeBottomAppBarTabs.Search -> {
                                WorkerSearch()
                            }
                            HomeBottomAppBarTabs.Watchlisted -> {
                                SavedWorkers(
                                    navigator = navigator,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}