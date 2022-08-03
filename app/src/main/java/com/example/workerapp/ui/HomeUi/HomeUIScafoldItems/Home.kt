package com.example.workerapp.ui.HomeUi.HomeUIScafoldItems
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.workerapp.ui.HomeUi.*
import com.example.workerapp.ui.HomeUi.HomeUiTabs.main
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainHolderComposable(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel()
) {
    val viewState by viewModel.state.collectAsState()
    Surface() {
        HomeScreen(
            navigator = navigator,
            showPlus = viewState.showPlus,
            homeSelectedTab = viewState.SelectedHomeBottomAppBarTabs,
            onClickWatchlist = viewModel::onClickWatchlist,
            onclickHomeBottomAppTab = viewModel::onClickHomeBottomAppTab,
            drawerState = viewState.drawerState
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    showPlus :Boolean,
    homeSelectedTab: HomeBottomAppBarTabs,
    onClickWatchlist: () -> Unit,
    onclickHomeBottomAppTab: (HomeBottomAppBarTabs) -> Unit,
    drawerState: DrawerState
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
                topBar(
                    title = homeSelectedTab.toString(),
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBarHomePage(homeSelectedTab, onclick = {onclickHomeBottomAppTab(it)})
            },
            content = {
                when (homeSelectedTab) {
                    HomeBottomAppBarTabs.Home -> {
                        main(
                            it, navigator, showPlus, onClickWatchlist
                        )
                    }
                    HomeBottomAppBarTabs.Search->{
                        WorkerSearch(it)
                    }
                    HomeBottomAppBarTabs.Watchlisted->{
                        SavedWorkers(it)
                    }
                }
            }
        )
    }
}




