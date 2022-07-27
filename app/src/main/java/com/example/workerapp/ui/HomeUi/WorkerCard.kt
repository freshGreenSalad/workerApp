package com.example.workerapp.ui.HomeUi


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Workers
import com.example.workerapp.R
import com.example.workerapp.ui.destinations.WorkerPageDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker:Workers,
    navigator: DestinationsNavigator
){
    Card(
        onClick = {
                  navigator.navigate(
                      WorkerPageDestination(
                          worker
                      )
                  )
        },
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        content = {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.weight(1f),
                    painter = painterResource(id = worker.image),
                    contentDescription = ""
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(text = worker.name)
                    Text(text = worker.price.toString())
                }
            }
        }
    )
}