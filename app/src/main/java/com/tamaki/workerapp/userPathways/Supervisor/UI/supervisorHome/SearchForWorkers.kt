package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUiTabs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.tamaki.workerapp.ui.components.*
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerSearch(
    viewModel: SupervisorViewModel
) {
    val state = viewModel.state.collectAsState()

    LazyColumnOfOrganisedComposables() {
        StandardSpacer()
        LargeTransperentText(text = "Years Of Experience ${state.value.range.start.toInt()} to ${state.value.range.endInclusive.toInt()}")
        StandardSpacer()
        RangeSlider(
            value = state.value.range,
            onValueChange = viewModel::updateRange,
            valueRange = 0f..20f,
            steps = 20
        )
        StandardSpacer()
        StandardButton(text = "Search") { (viewModel::searchForWorkers)() }
    }
}

