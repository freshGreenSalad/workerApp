package com.example.workerapp.ui.workerInfoPage.WorkerUITabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.R

@Composable
fun WorkerPhotoTab(
    paddingValues: PaddingValues,
    worker: WorkerTest
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
    worker: WorkerTest
){
   val imageURL = worker.imageURL
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                "https://testbucketletshopeitsfree.s3.ap-southeast-2.amazonaws.com/WorkerImages/"+imageURL
            )
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.four),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        contentDescription = ""
    )

    Row(verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text = worker.name)
        Text(text = worker.hourlyRate.toString())
    }
}