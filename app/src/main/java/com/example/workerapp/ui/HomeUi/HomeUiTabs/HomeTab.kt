package com.example.workerapp.ui.HomeUi.HomeUiTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Room.AppModuel.AWSConnection
import com.example.workerapp.Data.Room.Worker
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.ktor.AWSInterface
import com.example.workerapp.Data.Room.workerList
import com.example.workerapp.ui.HomeUi.Workercard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun main(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit,
    service: AWSInterface = AWSConnection()
) {
    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {

            val text = produceState(
                initialValue = "emptyText",
                producer = {
                    value = try {
                        Json.decodeFromString<WorkerTest>(service.getWorker(1)).toString()
                    } catch (e: Exception) {
                        ""
                    }
                }
            )
            Text(text.value)

            //URL image loader  Json.decodeFromString<Worker>
            /*AsyncImage(
                modifier = Modifier.size(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://testbucketletshopeitsfree.s3.ap-southeast-2.amazonaws.com/ImageTest"
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.four),
                contentDescription = ""
            )*/

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
            LazyRow{
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
            LazyRow {
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