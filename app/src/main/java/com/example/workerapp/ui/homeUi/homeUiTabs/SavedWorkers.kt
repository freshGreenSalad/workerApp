package com.example.workerapp.ui.homeUi

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.models.blankWorker
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.reflect.KSuspendFunction1


@Composable
fun SavedWorkers(
    paddingValues: PaddingValues,
    ListOfSavedWorkers: MutableList<Int>,
    navigator: DestinationsNavigator,
    removeFromWatchlist: (Int) -> Unit,
    addToWatchList: (Int) -> Unit,
    getworker: KSuspendFunction1<Int, String>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(ListOfSavedWorkers.size) { index ->
            val worker = produceState(
                initialValue = blankWorker,
                producer = {
                    value = try {
                        Json.decodeFromString(getworker(ListOfSavedWorkers[index]))
                    } catch (e: Exception) {
                        blankWorker
                    }
                }
            )
            Workercard(
                worker = worker,
                navigator = navigator,
                watchlistedWorkers = ListOfSavedWorkers,
                removeFromWatchlist = removeFromWatchlist,
                addToWatchList = addToWatchList
            )
        }
    }
}