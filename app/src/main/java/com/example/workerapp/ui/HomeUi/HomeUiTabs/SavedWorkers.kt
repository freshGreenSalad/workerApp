package com.example.workerapp.ui.HomeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.Data.Room.AppModuel
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.ktor.AWSInterface
import com.example.workerapp.Data.Room.workerTestTest
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


@Composable
fun SavedWorkers(
    paddingValues: PaddingValues,
    ListOfSavedWorkers: MutableList<Int>,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    service: AWSInterface = AppModuel.AWSConnection(),
    removeFromWatchlist: (Int) -> Unit ,
    addToWatchList:(Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp)
    ) {
        items(ListOfSavedWorkers.size) {index->
            val worker = produceState(
                initialValue = workerTestTest,
                producer = {
                    value = try {
                        Json.decodeFromString<WorkerTest>(service.getWorkerString(ListOfSavedWorkers[index]))
                    } catch (e: Exception) {
                        workerTestTest
                    }
                }
            )
            Workercard(worker, navigator, showPlus, ListOfSavedWorkers, removeFromWatchlist,
            addToWatchList)
        }
    }
}