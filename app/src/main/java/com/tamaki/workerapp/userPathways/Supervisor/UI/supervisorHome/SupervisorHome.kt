package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.R
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.WorkerCard
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.LargeTransperentText
import com.tamaki.workerapp.ui.components.textFormatBetweenSections
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel

@Composable
fun SupervisorHome(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    LazyColumn() {
        item {
            textFormatBetweenSections(stringResource(id = R.string.home_screen_findNewWorkers))
            WorkerLazyRowHome(navigator = navigator, viewModel = viewModel)
            textFormatBetweenSections(stringResource(id = R.string.home_screen_yourCrew))
            CrewSection()
            textFormatBetweenSections("Notices")
            Notices()
        }
    }
}

@Composable
private fun CrewSection() {
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
        LargeTransperentText("no workers in your crew")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Notices() {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
        .height(100.dp),
        content = { Text(text = "") }
    )
}

@Composable
fun WorkerLazyRowHome(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel
) {
    val viewstate by viewModel.state.collectAsState()
    LazyRow {
        for (worker in viewstate.workerList) {
            item {
                WorkerCard(
                    worker = worker,
                    navigator = navigator,
                    viewModel = viewModel
                )
            }
        }
    }
}