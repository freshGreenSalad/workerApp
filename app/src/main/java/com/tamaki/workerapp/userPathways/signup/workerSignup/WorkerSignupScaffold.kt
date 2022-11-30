package com.tamaki.workerapp.userPathways.signup.workerSignup

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.*
import com.tamaki.workerapp.destinations.WorkerProfileDestination
import com.tamaki.workerapp.userPathways.signup.workerSignup.tabs.WorkerSignupExperience
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.StepProgressBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupScaffold(
    viewModel: SignupViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.profileBuild.collect { result ->
            destinationsfromworkersignup(result, navigator, context)
        }
    }

    val viewStateExperience by viewModel.stateExperience.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {},
            bottomBar = {
                bottomBarWorkerSignUpPages(
                    viewModel = viewModel
                )
            },
            content = {
                workerSignupMainBody(
                    it,
                    navigator,
                    viewStateExperience.experienceType,
                    viewModel::updateExperienceType,
                    viewModel = viewModel
                )
            }
        )
    }
}


fun destinationsfromworkersignup(
    result: authResult<Unit>,
    navigator: DestinationsNavigator,
    context: Context
) {
    when (result) {
        is authResult.authorised<Unit> -> navigator.navigate(WorkerProfileDestination)
        is authResult.unauthorised<Unit> -> {
            Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
        }
        is authResult.unknownError<Unit> -> {
            Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
        }
        else -> {}
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun workerSignupMainBody(
    it: PaddingValues,
    navigator: DestinationsNavigator,
    ExperienceType: ExperienceType,
    ChangeExperienceType: (ExperienceType) -> Unit,
    viewModel: SignupViewModel
) {
    val viewState by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .padding(it)
            .fillMaxSize()
    ) {
        AnimatedContent(
            targetState = viewState.workerSignUpPoint,
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
            when (targetState) {
                WorkerSignUpPoint.BasicInformation -> {
                    BasicInformation(
                        navigator = navigator,
                        viewModel = viewModel
                    )
                }
                WorkerSignUpPoint.Experience -> {
                    WorkerSignupExperience(
                        experienceType = ExperienceType,
                        ChangeExperienceType = ChangeExperienceType,
                        viewModel = viewModel
                    )
                }
                WorkerSignUpPoint.Tickets -> {
                    WorkerSignupTickets(
                        viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun bottomBarWorkerSignUpPages(
    viewModel: SignupViewModel
) {
    val scope = rememberCoroutineScope()

    val viewState by viewModel.state.collectAsState()

    val animatedProgress by animateFloatAsState(
        targetValue = (viewState.currentStep.toFloat()*0.5f),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(10.dp, 10.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .padding(30.dp, 15.dp, 30.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            StepProgressBar(
                modifier = Modifier.fillMaxWidth(),
                currentStep = animatedProgress
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StandardButton("Back") {
                    if (viewState.currentStep == 0) {
                    } else (viewModel::nextScreen)(-1)
                }
                var text = if (viewState.workerSignUpPoint == WorkerSignUpPoint.Experience) "Done" else "Next"
                StandardButton(text) {
                    if (viewState.workerSignUpPoint == WorkerSignUpPoint.Experience) {
                        scope.launch { (viewModel::postPersonalWorker)() }
                    } else (viewModel::nextScreen)(1)
                }
            }
        }
    }
}

