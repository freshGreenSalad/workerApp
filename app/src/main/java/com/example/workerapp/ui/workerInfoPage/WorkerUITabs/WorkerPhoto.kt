package com.example.workerapp.ui.workerInfoPage.WorkerUITabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.workerapp.Data.Room.Workers

@Composable
fun WorkerPhotoTab(
    paddingValues: PaddingValues,
    worker: Workers
){
    Surface(
        modifier = androidx.compose.ui.Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        cardViewOfWorker(worker)
    }
}

@Composable
fun cardViewOfWorker(
    worker: Workers
){
    val image = painterResource(id = worker.image)
    Image(
        modifier = Modifier.fillMaxSize(),
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