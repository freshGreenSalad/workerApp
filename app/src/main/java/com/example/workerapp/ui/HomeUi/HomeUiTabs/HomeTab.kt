package com.example.workerapp.ui.HomeUi.HomeUiTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.Data.Room.workerList
import com.example.workerapp.ui.HomeUi.MainViewModel
import com.example.workerapp.ui.HomeUi.Workercard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun main(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
   // viewModel: MainViewModel,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
            val scope = rememberCoroutineScope()
            val worker2 = workerList[1]
            /*Button(
                onClick = {
                    scope.launch {
                        viewModel.addworker(worker2)
                    }
                },
            ) {}*/


           /* val workers: List<Workers> by viewModel.GetWorkerFlow()
                .collectAsState(initial = workerList)*/


           /* Text(text = workers.first().name)*/
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Your Crew"
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
            val items = workerList
            LazyRow() {
                items(items.size) { index ->
                    Workercard(items[index], navigator, showPlus, onClickWatchlist)
                }
            }
        }
        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Find New Workers"
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
            val items = workerList
            LazyRow() {
                items(items.size) { index ->
                    Workercard(items[index], navigator, showPlus, onClickWatchlist)
                }
            }
        }
        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Notices"
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
        items(3) { index ->
            Card(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(100.dp),
                content = { Text(text = "$index") }
            )
        }

    }
}