package com.example.workerapp.ui.workerInfoPage.WorkerScaffoldItems

import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.ui.HomeUi.MainDrawer
import com.example.workerapp.ui.HomeUi.topBar
import com.example.workerapp.ui.workerInfoPage.WorkerScaffoldItems.BottomAppBarWorkerPage
import com.example.workerapp.ui.workerInfoPage.WorkerUITabs.WorkerPhotoTab
import com.example.workerapp.ui.workerInfoPage.WorkerUITabs.WorkerStats
import com.example.workerapp.ui.workerInfoPage.WorkerUITabs.WorkersTools
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun WorkerPage(
    inter: Int,
    SelectedClickThroughWorker: WorkerTest,
    navigator: DestinationsNavigator
) {
    var selectedItem by remember { mutableStateOf(BottomAppBarWorkerPageCatagory.WorkerPhoto) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
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
                    title = selectedItem.toString(),
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBarWorkerPage(selectedItem, onclick = {selectedItem = it})
            },
            content = {
                when(selectedItem) {
                    BottomAppBarWorkerPageCatagory.WorkerPhoto -> {
                        WorkerPhotoTab(it,SelectedClickThroughWorker)
                    }
                    BottomAppBarWorkerPageCatagory.WorkerStats-> {
                        WorkerStats(it)
                    }
                    BottomAppBarWorkerPageCatagory.Tools -> {
                        WorkersTools(it)
                    }
                }
            }
        )
    }
}

enum class BottomAppBarWorkerPageCatagory{
    WorkerPhoto, WorkerStats, Tools
}