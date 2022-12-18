package com.tamaki.workerapp.userPathways.signup.workerSignup.tabs


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.ComposableThatSetsInitialScreenParameters
import com.tamaki.workerapp.ui.components.LazyColumnOfOrganisedComposables
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.ui.components.StandardSpacer

@Composable
fun WorkerSignupExperience(
    viewModel: SignupViewModel
) {
    val state = viewModel.stateLogin.collectAsState()
    ComposableThatSetsInitialScreenParameters() {
        StandardPrimaryTextHeading("Your experience")
        StandardSpacer()
        RangeSliderForWorkerSignup(viewModel)
        StandardSpacer()
        WorkerChipGroup(viewModel)
        StandardSpacer()
        StandardPrimaryTextHeading(text = "You have ${state.value.yearsExperience} years experience as a ${state.value.experience}")
    }
}

@Composable
private fun RangeSliderForWorkerSignup(
    viewModel: SignupViewModel
) {
    val state = viewModel.stateLogin.collectAsState()
    Slider(
        value = state.value.yearsExperience,
        onValueChange = viewModel::updateRange,
        valueRange = 0f..20f,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerChipGroup(
    viewModel:SignupViewModel,
){
    val experienceList = listOf("labourer", "formworker", "drain Layer" )
    ChipVerticalGrid(
        spacing = 7.dp,
        modifier = Modifier
            .padding(7.dp)
    ) {
        experienceList.forEach { word ->
            InputChip(selected = true,
                label = { Text(text = word) },
                onClick = {(viewModel::updateExperience)(word)}
            )
        }
    }
}

@Composable
fun ChipVerticalGrid(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var currentRow = 0
        var currentOrigin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentOrigin.x > 0f && currentOrigin.x + placeable.width > constraints.maxWidth) {
                currentRow += 1
                currentOrigin = currentOrigin.copy(x = 0, y = currentOrigin.y + placeable.height + spacingValue)
            }

            placeable to currentOrigin.also {
                currentOrigin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}