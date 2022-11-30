package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.BottomAppBarHomePage
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs.SupervisorHome
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs.WorkerSearch
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph(start = true)
@Destination
@Composable
fun MainHolderComposable(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val viewState by viewModel.state.collectAsState()
    
    Surface{
        HomeScreen(
            navigator = navigator,
            homeAppBarTabs = viewState.homeAppBarTabs,
            homeSelectedTab = viewState.selectedHomeBarTab,
            drawerState = viewState.drawerState,
            onclickHomeBottomAppTab = viewModel::onClickHomeBottomAppTab,
            ListOfSavedWorkers = viewState.savedWorkers,
            deleteFromDataStore = viewModel::deleteAllFromDataStore,
            getWorkerProfile = viewModel::getWorkerByEmail,
            deleteAccount = viewModel::deleteAccount,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeAppBarTabs:  List<HomeBottomAppBarTabs>,
    homeSelectedTab: HomeBottomAppBarTabs,
    onclickHomeBottomAppTab: (HomeBottomAppBarTabs) -> Unit,
    drawerState: DrawerState,
    ListOfSavedWorkers: MutableList<String>,
    deleteFromDataStore: KSuspendFunction0<Unit>,
    getWorkerProfile: KSuspendFunction1<String, WorkerProfile>,
    deleteAccount: KSuspendFunction0< Unit>,
    viewModel: SupervisorViewModel
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                navigator,
                deleteFromDataStore,
                closeDrawer = {scope.launch { drawerState.close() }},
                deleteAccount
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {
                TopBar(
                    title = homeSelectedTab.toString(),
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBarHomePage(
                    selectedItem = homeSelectedTab,
                    onclick = {onclickHomeBottomAppTab(it)},
                    homeAppBarTabs = homeAppBarTabs,
                    ListOfSavedWorkers = ListOfSavedWorkers
                )
            },
            content = {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(it)
                ) {
                    when (homeSelectedTab) {
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




