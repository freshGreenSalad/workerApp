package com.tamaki.workerapp.userPathways.signup.supervisorSignup

import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.*
import com.tamaki.workerapp.ui.screens.general.AddressScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.*

@ProfileCreationNavGraph
@Destination
@Composable
fun SupervisorSignupScaffold(
    viewModel: SignupViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.profileBuild.collect { result ->
            (viewModel::handleSupervisorSignupRequest)(result, navigator, context)
        }
    }

    ComposableThatSetsInitialScreenParameters {
        WorkerProfileScaffold(
            navigator = navigator,
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerProfileScaffold(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {
    val state by viewModel.stateLogin.collectAsState()
    val animatedProgress by animateFloatAsState(
        targetValue = (state.currentSupervisorStep.toFloat()+0.00001f),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Scaffold(
        modifier = Modifier.padding(.4.dp),
        topBar = {},
        bottomBar = { BottomBarSignUp(animatedProgress, viewModel) },
        content = {
            FillMaxSizePaddingBox(it){
                AnimateTransitionsBetweenMapScreenAndBasicInformationScreen(
                    navigator = navigator,
                    viewModel = viewModel
                )
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimateTransitionsBetweenMapScreenAndBasicInformationScreen(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {

    val state by viewModel.stateLogin.collectAsState()

    AnimatedContent(
        targetState = state.supervisorSignupPoint,
        transitionSpec = {
            val direction = if (initialState.ordinal < targetState.ordinal)
                AnimatedContentScope.SlideDirection.Left else AnimatedContentScope
                .SlideDirection.Right

            slideIntoContainer(
                towards = direction,
                animationSpec = tween(500)
            ) with
                    slideOutOfContainer(
                        towards = direction,
                        animationSpec = tween(500)
                    ) using SizeTransform(
                clip = false,
                sizeAnimationSpec = { _, _ ->
                    tween(500, easing = EaseInOut)
                }
            )
        }
    ) { targetState ->
        ShowMapComposableOrBasicInformationComposable(
            targetState = targetState,
            navigator = navigator,
            viewModel = viewModel
        )
    }
}

@Composable
private fun ShowMapComposableOrBasicInformationComposable(
    targetState: SupervisorSignupPoint,
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {
    when (targetState) {
        SupervisorSignupPoint.BasicInformation -> {
            SupervisorBasicInformation(
                navigator = navigator,
                viewModel = viewModel
            )
        }
        SupervisorSignupPoint.Map -> {
            AddressScreen(
                navigator = navigator,
                viewModel = viewModel
            )
        }
    }
}

