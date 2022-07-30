package com.example.workerapp.ui.HomeUi

import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Workers
import com.example.workerapp.ui.workerInfoPage.WorkerChipGroup
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun WorkerPage(
    worker:Workers
) {
    var selectedItem by remember { mutableStateOf(BottomAppBarWorkerPageCatagory.workerPhoto) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column() {
                Text(text = "drawer")
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {
                topBar(
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
                    BottomAppBarWorkerPageCatagory.workerPhoto -> {
                        Surface(
                            modifier = Modifier
                                .padding(it)
                                .fillMaxSize()
                        ) {
                            cardViewOfWorker(worker)
                        }
                    }
                    BottomAppBarWorkerPageCatagory.workerStats-> {
                        Column() {
                            Box(
                                modifier = Modifier.padding(it).height(200.dp)
                            ) {
                                anamatedWorkHistory()
                            }
                            WorkerChipGroup()
                        }
                    }
                }
            }
        )
    }
}

enum class BottomAppBarWorkerPageCatagory{
    workerPhoto, workerStats, extra
}

@Composable
fun BottomAppBarWorkerPage(
    selectedItem: BottomAppBarWorkerPageCatagory,
    onclick: (BottomAppBarWorkerPageCatagory) -> Unit
){
    //var selectedItem by remember { mutableStateOf(BottomAppBarWorkerPageCatagory.workerPhoto) }
    val values = BottomAppBarWorkerPageCatagory.values().map { it.name }
    NavigationBar() {
        values.forEachIndexed(){index, title ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(title) },
                selected = title == selectedItem.toString(),
                onClick = {onclick(BottomAppBarWorkerPageCatagory.values()[index]) }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cardViewOfWorker(
    worker: Workers
){
    Card(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .height(500.dp),
    elevation = CardDefaults.cardElevation(10.dp)) {
        val image = painterResource(id = worker.image)
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = image,
                contentDescription = "",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Row(verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = worker.name)
                    Text(text = worker.price.toString())
            }
        }
    }
}