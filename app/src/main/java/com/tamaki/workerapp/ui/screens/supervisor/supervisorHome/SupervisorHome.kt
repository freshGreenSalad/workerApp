package com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUiTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.Workercard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupervisorHome(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    watchlistedWorkers: MutableList<String>,
    removeFromWatchlist: (String) -> Unit,
    addToWatchList: (String) -> Unit,
    workerList: List<WorkerProfile>
) {
    val scope = rememberCoroutineScope()


    LazyColumn(modifier = Modifier.padding(paddingValues)) {

        item {
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
                workerList = workerList
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
        item {
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
            ) {
                Text(
                    text = "no workers in your crew",
                    modifier = Modifier
                        .padding(20.dp)
                        .alpha(0.5f),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        item {
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
    watchlistedWorkers: MutableList<String>,
    removeFromWatchlist: (String) -> Unit,
    addToWatchList: (String) -> Unit,
    workerList: List<WorkerProfile>
) {
    LazyRow {
        for (worker in workerList) {
            item {
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
}