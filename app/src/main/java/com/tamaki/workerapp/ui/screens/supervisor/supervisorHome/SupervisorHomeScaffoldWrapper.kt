package com.tamaki.workerapp.ui.screens.supervisor.supervisorHome

import com.tamaki.workerapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.BottomAppBarHomePage
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUiTabs.SupervisorHome
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUiTabs.WorkerSearch
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph
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
            removeFromWatchlist = viewModel::removeFromWatchlist,
            addToWatchList = viewModel::addToWatchList,
            deleteFromDataStore = viewModel::deleteAllFromDataStore,
            getWorkerProfile = viewModel::getWorkerByEmail,
            workerListSize = viewState.workerListSize,
            workerList = viewState.workerList,
            deleteAccount = viewModel::deleteAccount
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
    removeFromWatchlist: (String) -> Unit,
    addToWatchList:(String) -> Unit,
    deleteFromDataStore: KSuspendFunction0<Unit>,
    getWorkerProfile: KSuspendFunction1<String, WorkerProfile>,
    workerListSize: Int,
    workerList: List<WorkerProfile>,
    deleteAccount: KSuspendFunction0< Unit>
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (homeSelectedTab) {
                        HomeBottomAppBarTabs.Home -> {
                            SupervisorHome(
                                paddingValues = it,
                                navigator = navigator,
                                watchlistedWorkers = ListOfSavedWorkers,
                                removeFromWatchlist = removeFromWatchlist,
                                addToWatchList = addToWatchList,
                                workerListSize = workerListSize,
                                workerList = workerList
                            )
                        }
                        HomeBottomAppBarTabs.Search -> {
                            WorkerSearch(
                                paddingValues = it
                            )
                        }
                        HomeBottomAppBarTabs.Watchlisted -> {
                            SavedWorkers(
                                paddingValues = it,
                                ListOfSavedWorkers = ListOfSavedWorkers,
                                navigator = navigator,
                                removeFromWatchlist = removeFromWatchlist,
                                addToWatchList = addToWatchList,
                                getWorkerProfile = getWorkerProfile
                            )
                        }
                    }
                }
            }
        )
    }
}




