package com.example.workerapp.ui.HomeUi


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Workers
import com.example.workerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker:Workers
){
    Card(modifier = Modifier
        .padding(8.dp)
        .size(width = 120.dp, height = 180.dp),
        content = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(painter = painterResource(id = worker.image), contentDescription = "")
                Row(modifier = Modifier
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