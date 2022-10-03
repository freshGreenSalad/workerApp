package com.example.workerapp.ui.screens.general.workerSignup

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.SignupSigninViewModel
import com.example.workerapp.data.viewModel.WorkerSignUpPoint
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupScaffold(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {
    val viewState by viewModel.state.collectAsState()

    val viewStateName by viewModel.stateName.collectAsState()

    val viewStateCamera by viewModel.stateCamera.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        WorkerProfileScaffold(
            navigator = navigator,
            workerSignupPoint = viewState.workerSignUpPoint,
            nextScreen = viewModel::nextScreen,
            firstname = viewStateName.firstname,
            lastname = viewStateName.lastname,
            UpdateFirstname = viewModel::updateFirstname,
            UpdateLastname = viewModel::updateLastname,
            photoUri = viewStateCamera.photoUri,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun WorkerProfileScaffold(
    navigator: DestinationsNavigator,
    workerSignupPoint: WorkerSignUpPoint,
    nextScreen: () -> Unit,
    firstname: String,
    lastname: String,
    UpdateFirstname: (String) -> Unit,
    UpdateLastname: (String) -> Unit,
    photoUri: Uri,
) {
    Scaffold(
        modifier = Modifier.padding(.4.dp),
        topBar = {},
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(10.dp)
                    .background(
                        shape = RoundedCornerShape(5.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    .clickable {
                        nextScreen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Done!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },

        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                AnimatedContent(
                    targetState = workerSignupPoint,
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
                        WorkerSignUpPoint.basicinformation -> {
                            BasicInformation(
                                firstname = firstname,
                                lastname = lastname,
                                UpdateFirstname = UpdateFirstname,
                                UpdateLastname = UpdateLastname,
                                photoUri = photoUri,
                                navigator = navigator
                            )
                        }
                        WorkerSignUpPoint.Experience -> {
                            Text(text = "Experience")
                        }
                        WorkerSignUpPoint.tickets -> {
                            Text(text = "tickets")
                        }
                    }
                }
            }
        }
    )
}

