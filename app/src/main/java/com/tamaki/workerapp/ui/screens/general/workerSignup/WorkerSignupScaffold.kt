package com.tamaki.workerapp.ui.screens.general.workerSignup

import com.tamaki.workerapp.R
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.*
import com.tamaki.workerapp.destinations.WorkerProfileDestination
import com.tamaki.workerapp.ui.screens.general.workerSignup.tabs.WorkerSignupExperience
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0


@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupScaffold(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.profileBuild.collect { result ->
            when (result) {
                is authResult.authorised<Unit> -> navigator.navigate(WorkerProfileDestination)
                is authResult.unauthorised<Unit> -> {
                    Log.d("signup", "unauthorised block")
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                is authResult.unknownError<Unit> -> {
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Log.d("else block","of the worker signup scafold result direction launched effect block ")
                }
            }

        }
    }
    val viewState by viewModel.state.collectAsState()

    val viewStateName by viewModel.stateName.collectAsState()

    val viewStateCamera by viewModel.stateCamera.collectAsState()

    val viewStateTickets by viewModel.stateTickets.collectAsState()

    val viewStateExperience by viewModel.stateExperience.collectAsState()

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
            UpdateHighestClass = viewModel::changeHighestClass,
            ExperienceType = viewStateExperience.experienceType,
            ChangeExperienceType = viewModel::updateExperienceType,
            FormworkMap = viewStateExperience.formworkMap,
            updateFormworkMap = viewModel::updateFormworkMap,
            currentStep = viewState.currentStep,
            postWorkerProfile = viewModel::postPersonalWorker
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun WorkerProfileScaffold(
    currentStep: Int,
    navigator: DestinationsNavigator,
    workerSignupPoint: WorkerSignUpPoint,
    nextScreen: (Int) -> Unit,
    firstname: String,
    lastname: String,
    UpdateFirstname: (String) -> Unit,
    UpdateLastname: (String) -> Unit,
    photoUri: Uri,
    ticketType: TicketType,
    ChangeTicketType: (TicketType) -> Unit,
    LicenceType: TypeOfLicence,
    ChangeLicenceType: (TypeOfLicence) -> Unit,
    licenceMap: Map<String, Boolean>,
    updateLicenceEntry: (String) -> Unit,
    highestClass: HighestClass,
    UpdateHighestClass: (HighestClass) -> Unit,
    ExperienceType: ExperienceType,
    ChangeExperienceType: (ExperienceType) -> Unit,
    FormworkMap: Map<String, Boolean>,
    updateFormworkMap: (String) -> Unit,
    postWorkerProfile: KSuspendFunction0<Unit>
) {
    val animatedProgress by animateFloatAsState(
        targetValue = (currentStep.toFloat()*0.5f),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.padding(.4.dp),
        topBar = {},
        bottomBar = {
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
                        Button(
                            onClick = {
                                if (currentStep == 0) { } else nextScreen(-1)
                            }
                        ) {
                            Text(text = "Back")
                        }
                        Button(
                            onClick = {
                                if (workerSignupPoint == WorkerSignUpPoint.Experience) {
                                    scope.launch {
                                        Log.d("button at the top of signup page", "button clicked")
                                        postWorkerProfile()
                                        Log.d("button at the bottom of signup page", "button clicked")
                                    }
                                } else nextScreen(1)
                            }
                        ) {
                            Text(text = if (workerSignupPoint == WorkerSignUpPoint.Experience) "Done" else "Next")
                        }
                    }
                }
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
                                navigator = navigator,
                            )
                        }
                        WorkerSignUpPoint.Experience -> {
                            WorkerSignupExperience(
                                experienceType = ExperienceType,
                                ChangeExperienceType = ChangeExperienceType,
                                FormworkMap = FormworkMap,
                                updateFormworkMap = updateFormworkMap,
                            )
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

@Composable
fun Step(
    modifier: Modifier = Modifier,
    isFirst: Boolean,
    isComplete: Boolean,
    isCurrent: Boolean
) {
    val color = if (isComplete || isCurrent) MaterialTheme.colorScheme.primary else Color.LightGray
    val innerCircleColor = if (isComplete) MaterialTheme.colorScheme.primary else Color.LightGray

    Box(
        modifier = modifier
    ) {
        if (!isFirst) {
            Divider(
                modifier = Modifier.align(
                    Alignment.CenterStart
                ),
                color = color,
                thickness = 5.dp
            )
        }

        //Stage step circle
        Canvas(
            modifier = Modifier
                .size(25.dp)
                .align(Alignment.CenterEnd)
                .border(
                    shape = CircleShape,
                    width = 5.dp,
                    color = color
                )
        ) {
            drawCircle(color = innerCircleColor)
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

