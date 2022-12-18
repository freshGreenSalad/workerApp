package com.tamaki.workerapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.viewModel.SupervisorSignupPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomBarSignUp(
    animatedProgress: Float,
    viewModel: SignupViewModel
) {
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp, 10.dp)
            )

    ) {
        Column(
            modifier = Modifier.padding(30.dp, 15.dp, 30.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            StepProgressBar(modifier = Modifier.fillMaxWidth(), currentStep = animatedProgress)
            Spacer(modifier = Modifier.height(5.dp))
            TwoButtons_nextScreen_PreviousScreen(scope, viewModel = viewModel)
        }
    }
}

@Composable
private fun TwoButtons_nextScreen_PreviousScreen(
    scope: CoroutineScope,
    viewModel: SignupViewModel
) {
    val state by viewModel.stateLogin.collectAsState()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StandardButton(text = "Back") {
            if (state.currentSupervisorStep == 0) { } else (viewModel::incrementSupervisorEnumPage)(-1) }
            val text = if (state.supervisorSignupPoint == SupervisorSignupPoint.Map) "Done" else "Next"
            StandardButton(text = text) {
                if (state.supervisorSignupPoint == SupervisorSignupPoint.Map) {
                    scope.launch { (viewModel::postSupervisorPersonalAndSite)() }
                } else (viewModel::incrementSupervisorEnumPage)(1)
            }
        }
    }


@Composable
fun StepProgressBar(
    modifier: Modifier = Modifier,
    currentStep: Float
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinearProgressIndicator(
            progress = currentStep,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.background
        )
    }
}