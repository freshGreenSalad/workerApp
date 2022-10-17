package com.tamaki.workerapp.ui.screens.general.supervisorSignup

import com.tamaki.workerapp.R
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
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
import com.tamaki.workerapp.destinations.MainHolderComposableDestination
import com.tamaki.workerapp.ui.screens.general.AddressScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@ProfileCreationNavGraph
@Destination
@Composable
fun SupervisorSignupScaffold(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.profileBuild.collect { result ->
            when (result) {
                is authResult.authorised<Unit> -> navigator.navigate(MainHolderComposableDestination)
                is authResult.unauthorised<Unit> -> {
                    Log.d("signup", "unauthorised block")
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                is authResult.unknownError<Unit> -> {
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Log.d(
                        "else block",
                        "of the worker signup scafold result direction launched effect block "
                    )
                }
            }

        }
    }

    val mapState by viewModel.stateMap.collectAsState()

    val viewSupervisorStateCamera by viewModel.stateSupervisorCamera.collectAsState()

    val supervisorState by viewModel.stateSupervisorScaffold.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        WorkerProfileScaffold(
            navigator = navigator,
            supervisorSignUpPoint = supervisorState.supervisorSignupPoint,
            nextScreen = viewModel::nextSupervisorScreen,
            firstname = supervisorState.supervisorFirstName,
            lastname = supervisorState.supervisorLastName,
            UpdateFirstname = viewModel::updateSupervisorFirstName,
            UpdateLastname = viewModel::updateSupervisorLastName,
            photoUri = viewSupervisorStateCamera.photoUri,
            currentStep = supervisorState.currentSupervisorStep,
            mapState = mapState.map,
            siteAddress = mapState.siteAddress,
            updateSiteAddress = viewModel::updateSiteAddress,
            postSupervisorProfile = viewModel::postSupervisorPersonalAndSite
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun WorkerProfileScaffold(
    currentStep: Int,
    navigator: DestinationsNavigator,
    supervisorSignUpPoint: SupervisorSignupPoint,
    nextScreen: (Int) -> Unit,
    firstname: String,
    lastname: String,
    UpdateFirstname: (String) -> Unit,
    UpdateLastname: (String) -> Unit,
    photoUri: Uri,
    mapState: MapDataClass,
    siteAddress: String,
    updateSiteAddress:(String)->Unit,
    postSupervisorProfile: KSuspendFunction0<Unit>
) {
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
                        numberOfSteps = 1,
                        currentStep = currentStep
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                if (currentStep == 0) {
                                } else nextScreen(-1)
                            }
                        ) {
                            Text(text = "Back")
                        }
                        Button(
                            onClick = {
                                if (supervisorSignUpPoint == SupervisorSignupPoint.Map) {
                                    scope.launch {
                                        postSupervisorProfile()
                                    }
                                } else nextScreen(1)
                            }
                        ) {
                            Text(text = if (supervisorSignUpPoint == SupervisorSignupPoint.Map) "Done" else "Next")
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
                    targetState = supervisorSignUpPoint,
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
                        SupervisorSignupPoint.BasicInformation -> {
                            SupervisorBasicInformation(
                                firstname = firstname,
                                lastname = lastname,
                                UpdateFirstname = UpdateFirstname,
                                UpdateLastname = UpdateLastname,
                                photoUri = photoUri,
                                navigator = navigator
                            )
                        }
                        SupervisorSignupPoint.Map -> {
                            AddressScreen(
                                navigator = navigator,
                                map = mapState,
                                siteAddress = siteAddress,
                                updateSiteAddress = updateSiteAddress
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
    numberOfSteps: Int,
    currentStep: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0..numberOfSteps) {
            val isFirst = step == 0
            Step(
                isFirst = isFirst,
                isComplete = step < currentStep,
                isCurrent = step == currentStep,
                modifier = Modifier.weight(if (isFirst) .3f else 2f)
            )
        }
    }
}

