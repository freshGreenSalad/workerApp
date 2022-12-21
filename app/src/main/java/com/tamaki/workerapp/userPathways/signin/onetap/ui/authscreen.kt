package com.tamaki.workerapp.userPathways.signin.onetap.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.animation.*
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.navgraphs.OnetapNavGraph
import com.tamaki.workerapp.destinations.ProfileScreenDestination
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.StandardSpacer
import com.tamaki.workerapp.ui.components.TextFieldWithKeyboardActions
import com.tamaki.workerapp.ui.screens.general.signup.ButtonWithTickIfTrue
import com.tamaki.workerapp.userPathways.signin.onetap.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OnetapNavGraph(start = true)
@Destination
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    if (viewModel.isUserAuthenticated) {
        navigator.navigate(ProfileScreenDestination)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        Alignment.Center
    ) {
        backgroundmovie()
        WrapperAnimateSlideInFromRight() {
            SignInSignUpBox(navigator, viewModel)
        }
    }


    val launcher = rememberLauncherForActivityResult(StartIntentSenderForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            try {
                val credentials = viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                val googleIdToken = credentials.googleIdToken
                val googleCredentials = getCredential(googleIdToken, null)
                viewModel.signInWithGoogle(googleCredentials)
            } catch (it: ApiException) {
                print(it)
            }
        }
    }

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(viewModel, launch = { launch(it) })
    SignInWithGoogle(viewModel, navigator)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SignInSignUpBox(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel
) {
    var state by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),
                shape = RoundedCornerShape(15.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.01f),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)),
            selectedTabIndex = state) {
            Tab(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),shape = RoundedCornerShape(topStart = 15.dp)),
                selected = state == 0,
                onClick = { state = 0 },
                text = { Text(text = "Login", maxLines = 2, overflow = TextOverflow.Ellipsis) }
            )
            Tab(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),shape = RoundedCornerShape(topEnd = 15.dp)),
                selected = state == 1,
                onClick = { state = 1 },
                text = { Text(text = "Signup", maxLines = 2, overflow = TextOverflow.Ellipsis) }
            )

        }
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                val direction = if (initialState < targetState)
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
                0 -> { SignInBox(navigator, viewModel) }
                1 -> { signup(viewModel) }
            }
        }
        StandardSpacer()
    }
}

@Composable
fun signup(
    viewModel: AuthViewModel
){
    val scope = rememberCoroutineScope()
    val state = viewModel.stateLogin.collectAsState().value
    Column {
        StandardSpacer()
        ButtonWithTickIfTrue(
            true,
            isSupervisorState = state.isSupervisor,
            text = "Supervisor",
            function = { (viewModel::updateIsSupervisor)(true) })
        StandardSpacer()
        ButtonWithTickIfTrue(
            false,
            isSupervisorState = state.isSupervisor,
            text = "Worker",
            function = { (viewModel::updateIsSupervisor)(false) })
        StandardSpacer()
        TextFieldWithKeyboardActions("Email", viewModel::updateEmail, state.email)
        StandardSpacer()
        TextFieldWithKeyboardActions("Password", viewModel::updatePassword, state.password)
        StandardSpacer()
        StandardButton("Sign up") {
            scope.launch {}// (viewModel::postEmailPasswordIsSupervisor)() }
        }
    }

}

@Composable
fun SignInBox(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel
) {
    val viewState by viewModel.stateLogin.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        (viewModel::returnAToastOrANavigationPathwayDependingOnLoginDetails)(
            navigator,
            viewModel,
            context
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardSpacer()
        TextFieldWithKeyboardActions(
            text = "Enter email",
            function = viewModel::updateEmailField,
            textFieldValue = viewState.email
        )
        StandardSpacer()
        TextFieldWithKeyboardActions(
            text = "Enter password",
            function = viewModel::updatePasswordField,
            textFieldValue = viewState.password
        )
        StandardSpacer()
        StandardButton(
            text = "Login",
            function = viewModel::tryToLoginToAccountWhenClickingOnButton
        )
        StandardSpacer()
        signInWithGoogle(viewModel::oneTapSignIn)
    }
}

@Composable
fun backgroundmovie() {
    AndroidView(
        factory = {
            FixedSizeVideoView(it).apply {
                setVideoURI(Uri.parse("android.resource://com.tamaki.workerapp.debug/${R.raw.construction}"))
                setOnPreparedListener { mp ->
                    mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    mp.isLooping = true
                    mp.setScreenOnWhilePlaying(false)
                }
                start()
            }
        },
    )
}

@Composable
fun WrapperAnimateSlideInFromRight(composable: @Composable() () -> Unit) {

    val scope = rememberCoroutineScope()
    var showBox by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        scope.launch {
            delay(2000L)
            showBox = true
        }
    }

    AnimatedVisibility(
        visible = showBox,
        enter = slideInHorizontally(),
    ) {
        composable()
    }


}

class FixedSizeVideoView(ctx: Context) : VideoView(ctx) {
    private var mVideoWidth = 0
    private var mVideoHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                height = width * mVideoHeight / mVideoWidth
            } else if (mVideoWidth * height < width * mVideoHeight) {
                width = height * mVideoWidth / mVideoHeight
            } else {
            }
        }
        setMeasuredDimension(width, height)
    }
}