package com.example.workerapp.ui.workerProfile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun WorkerHomeProfile(
    paddingValues: PaddingValues
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {

        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Your Stats"
            )
        }
        item {
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            WorkerHomeProfileLazyRow()
        }
    }
}

@Composable
fun WorkerHomeProfileLazyRow(){
    LazyRow(){

    }
}