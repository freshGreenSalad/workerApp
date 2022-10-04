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
import androidx.lifecycle.ViewModel
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.*
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

    val viewStateTickets by viewModel.stateTickets.collectAsState()

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
            ticketType = viewStateTickets.ticketType,
            ChangeTicketType = viewModel::changeTicketType,
            LicenceType = viewStateTickets.licenceType,
            ChangeLicenceType = viewModel::changeLicenceType,
            licenceMap = viewStateTickets.licenceMap,
            updateLicenceEntry = viewModel::updateLicenceMap,
            highestClass = viewStateTickets.highestClass,
            UpdateHighestClass = viewModel::changeHighestClass
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
    ticketType: TicketType,
    ChangeTicketType: (TicketType)->Unit,
    LicenceType: TypeOfLicence,
    ChangeLicenceType: (TypeOfLicence) -> Unit,
    licenceMap: Map<String, Boolean>,
    updateLicenceEntry:(String)->Unit,
    highestClass: HighestClass,
    UpdateHighestClass: (HighestClass) -> Unit
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
                        WorkerSignUpPoint.BasicInformation -> {
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
                        WorkerSignUpPoint.Tickets -> {
                            WorkerSignupTickets(
                                ticketType = ticketType,
                                ChangeTicketType = ChangeTicketType,
                                LicenceType = LicenceType,
                                ChangeLicenceType = ChangeLicenceType,
                                licenceMap = licenceMap,
                                updateLicenceEntry = updateLicenceEntry,
                                highestClass = highestClass,
                                UpdateHighestClass = UpdateHighestClass
                            )
                        }
                    }
                }
            }
        }
    )
}

