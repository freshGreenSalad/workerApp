package com.tamaki.workerapp.userPathways.signin.onetap.ui

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.OnetapNavGraph
import com.tamaki.workerapp.destinations.ProfileScreenDestination
import com.tamaki.workerapp.destinations.SignupDestination
import com.tamaki.workerapp.ui.components.LogoImageBox
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.StandardSpacer
import com.tamaki.workerapp.ui.components.TextFieldWithKeyboardActions
import com.tamaki.workerapp.userPathways.signin.onetap.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                backgroundmovie(padding, viewModel)
                boxThatSlidesInFromtheRight()
            }
        }
    )

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

    OneTapSignIn(viewModel, launch ={launch(it)})
    SignInWithGoogle(viewModel, navigator)
}

@Composable
fun SignInBox(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel
) {

    val viewState by viewModel.stateLogin.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        (viewModel::returnAToastOrANavigationPathwayDependingOnLoginDetails)(navigator, viewModel, context)
    }

    Column {
        LogoImageBox()
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
        StandardButton(text = "Sign up") { navigator.navigate(SignupDestination) }
        StandardSpacer()
    }
}

@Composable
fun backgroundmovie(
    padding:PaddingValues,
    viewModel: AuthViewModel
){
    val videoItems by viewModel.videoItems.collectAsState()
    val selectVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let(viewModel::addVideoUri)
        }
    )
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                }
            },
            update = {
                when (lifecycle) {
                    Lifecycle.Event.ON_PAUSE -> {
                        it.onPause()
                        it.player?.pause()
                    }
                    Lifecycle.Event.ON_RESUME -> {
                        it.onResume()
                    }
                    else -> Unit
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(8 / 15f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        IconButton(onClick = {
            selectVideoLauncher.launch("video/mp4")
        }) {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "Select video"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(videoItems) { item ->
                Text(
                    text = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.playVideo(item.contentUri)
                        }
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun boxThatSlidesInFromtheRight(){

}