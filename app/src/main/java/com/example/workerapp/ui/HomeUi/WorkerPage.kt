package com.example.workerapp.ui.HomeUi

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Workers
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun WorkerPage(
    worker:Workers
) {
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
                BottomAppBar()
            },
            content = {
                Surface(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                ) {
                    cardViewOfWorker(worker)
                }
            }
        )
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