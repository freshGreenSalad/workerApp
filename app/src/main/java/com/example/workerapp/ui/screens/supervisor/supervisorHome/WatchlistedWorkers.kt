package com.example.workerapp.ui.screens.supervisor.supervisorHome

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.example.workerapp.data.dataClasses.workerDataClasses.workerProfileFail
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.reflect.KSuspendFunction1


@Composable
fun SavedWorkers(
    paddingValues: PaddingValues,
    ListOfSavedWorkers: MutableList<String>,
    navigator: DestinationsNavigator,
    removeFromWatchlist: (String) -> Unit,
    addToWatchList: (String) -> Unit,
    getWorkerProfile: KSuspendFunction1<String, WorkerProfile>
) {
    if (ListOfSavedWorkers.size == 0) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .alpha(0.5f),
            text = "No workers in your watchlist",
            style = MaterialTheme.typography.headlineSmall

        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(ListOfSavedWorkers.size) { size ->
                for (index in 0..size) {
                    val email = ListOfSavedWorkers[index]

                    val worker = produceState(
                        initialValue = workerProfileFail,
                        producer = {
                            value = getWorkerProfile(email)
                        }
                    )
                    Workercard(
                        worker = worker.value,
                        navigator = navigator,
                        watchlistedWorkers = ListOfSavedWorkers,
                        removeFromWatchlist = removeFromWatchlist,
                        addToWatchList = addToWatchList
                    )
                }
            }
        }
    }
}