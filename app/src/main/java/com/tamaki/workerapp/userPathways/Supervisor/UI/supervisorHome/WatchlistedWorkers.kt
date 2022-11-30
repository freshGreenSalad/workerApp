package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.workerProfileFail
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.LargeTransperentText

@Composable
fun SavedWorkers(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val state by viewModel.state.collectAsState()
    if (state.savedWorkers.size == 0) {
        LargeTransperentText("No workers in your watchlist")
    } else {
        GridOfWatchlistedWorkers(
            navigator,
            viewModel
        )
    }
}

@Composable
private fun GridOfWatchlistedWorkers(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val state by viewModel.state.collectAsState()
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp)
    ) {
        for (email in state.savedWorkers) {
            item {
                workercard(
                    email,
                    navigator,
                    viewModel
                )
            }
        }
    }
}

@Composable
private fun workercard(
    email: String,
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val worker = produceState(
        initialValue = workerProfileFail,
        producer = {
            value = (viewModel::getWorkerByEmail)(email)
        }
    )
    WorkerCard(
        worker = worker.value,
        navigator = navigator,
        viewModel = viewModel
    )
}