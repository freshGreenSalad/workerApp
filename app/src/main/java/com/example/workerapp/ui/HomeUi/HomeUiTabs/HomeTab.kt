package com.example.workerapp.ui.HomeUi.HomeUiTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Room.AppModuel.AWSConnection
import com.example.workerapp.Data.Room.ktor.AWSInterface
import com.example.workerapp.Data.Room.workerList
import com.example.workerapp.ui.HomeUi.Workercard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun main(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit,
    service: AWSInterface = AWSConnection()
) {

    val text = produceState<String>(
        initialValue = "emptyText",
        producer = {
            value = service.gethelloWorld()
        }
    )

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
               //Box(modifier = Modifier.size(180.dp).background(MaterialTheme.colorScheme.primary, TriangleShape()))
            Text(
                text = text.toString()
            )
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

/*
val scope = rememberCoroutineScope()
val worker2 = workerList[1]
*/
/*Button(
    onClick = {
        scope.launch {
            viewModel.addworker(worker2)
        }
    },
) {}*//*



*/
/* val workers: List<Workers> by viewModel.GetWorkerFlow()
     .collectAsState(initial = workerList)*//*



*/
/* Text(text = workers.first().name)*/
