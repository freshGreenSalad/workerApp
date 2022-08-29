package com.example.workerapp.ui.homeUi.homeUIScafoldItems

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workerapp.ui.homeUi.*
import com.example.workerapp.ui.homeUi.homeUiTabs.Main
import com.example.workerapp.ui.homeUi.homeUiTabs.WorkerSearch
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun MainHolderComposable(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
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
            getWorker = viewModel::getworker
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
    ListOfSavedWorkers: MutableList<Int>,
    removeFromWatchlist: (Int) -> Unit,
    addToWatchList:(Int) -> Unit,
    getWorker: KSuspendFunction1<Int, String>
) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                navigator
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
                            Main(
                                paddingValues = it,
                                navigator = navigator,
                                watchlistedWorkers = ListOfSavedWorkers,
                                removeFromWatchlist = removeFromWatchlist,
                                addToWatchList = addToWatchList,
                                getworker = getWorker
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
                                getworker = getWorker
                            )
                        }
                    }
                }
            }
        )
    }
}




