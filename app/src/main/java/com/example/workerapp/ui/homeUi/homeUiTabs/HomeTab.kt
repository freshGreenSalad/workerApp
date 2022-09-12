package com.example.workerapp.ui.homeUi.homeUiTabs

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.models.blankWorker
import com.example.workerapp.R
import com.example.workerapp.data.models.testProfile
import com.example.workerapp.ui.homeUi.Workercard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlin.reflect.KSuspendFunction1
import kotlin.reflect.KSuspendFunction2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    watchlistedWorkers: MutableList<Int>,
    removeFromWatchlist: (Int) -> Unit,
    addToWatchList: (Int) -> Unit,
    getworker: KSuspendFunction1<Int, String>,
    readFromDataStore: KSuspendFunction1<String, String?>
) {
    val context = LocalContext.current
    val jwtVal = produceState(
        initialValue = "testProfile",
        producer = {value = readFromDataStore("JWT")?:"sdfg"}
    )

    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        item {
            Text(jwtVal.value)
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.home_screen_findNewWorkers)
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
            WorkerLazyRowHome(
                navigator = navigator,
                watchlistedWorkers = watchlistedWorkers,
                removeFromWatchlist = removeFromWatchlist,
                addToWatchList = addToWatchList,
                getworker = getworker
            )
        }
        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.home_screen_yourCrew)
            )
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item{
            Surface(
                shape = RoundedCornerShape(15.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                    .size(width = 400.dp, height = 180.dp),
                shadowElevation = 20.dp,
            ){
                Text(
                    text = "no workers in your crew",
                    modifier = Modifier.padding(20.dp).alpha(0.5f),
                    style = MaterialTheme.typography.headlineSmall
                    )
            }
        }
        item{
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Notices"
            )
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


@Composable
fun WorkerLazyRowHome(
    navigator: DestinationsNavigator,
    getworker: KSuspendFunction1<Int, String>,
    watchlistedWorkers: MutableList<Int>,
    removeFromWatchlist: (Int) -> Unit,
    addToWatchList: (Int) -> Unit,
){
    LazyRow {
        items(5) { index ->
            val worker = produceState(
                initialValue = blankWorker,
                producer = {
                    value = try {
                        Json.decodeFromString(getworker(index))
                    } catch (e: Exception) {
                        blankWorker
                    }
                }
            )
            Workercard(
                worker = worker,
                navigator = navigator,
                watchlistedWorkers = watchlistedWorkers,
                removeFromWatchlist = removeFromWatchlist,
                addToWatchList = addToWatchList
            )
        }
    }
}