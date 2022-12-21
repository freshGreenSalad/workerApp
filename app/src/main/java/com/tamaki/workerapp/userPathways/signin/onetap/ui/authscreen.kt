package com.tamaki.workerapp.userPathways.signin.onetap.ui

import android.app.Activity.RESULT_OK
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.OnetapNavGraph
import com.tamaki.workerapp.destinations.ProfileScreenDestination
import com.tamaki.workerapp.ui.components.*
import com.tamaki.workerapp.ui.components.animatedComposableStructures.WrapperAnimateSlideInFromRight
import com.tamaki.workerapp.ui.screens.general.signup.ButtonWithTickIfTrue
import com.tamaki.workerapp.userPathways.signin.onetap.AuthViewModel
import com.tamaki.workerapp.userPathways.signin.onetap.LoginState
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
    ComposableThatSetsInitialScreenParameters {
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

@Composable
fun SignInSignUpBox(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel
) {
    ColumnWithAlphaBackground() {
        TabRowToChangeLoginSignin(viewModel)
        AnimateContentFromLoginToSignin(navigator, viewModel)
        StandardSpacer()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimateContentFromLoginToSignin(
    navigator: DestinationsNavigator,
    viewModel: AuthViewModel
) {
    val state = viewModel.stateLogin.collectAsState().value
    AnimatedContent(
        targetState = state.LoginOrSignIn,
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
            0 -> {
                LoginBox(navigator, viewModel)
            }
            1 -> {
                signup(viewModel)
            }
        }
    }
}

@Composable
private fun TabRowToChangeLoginSignin(viewModel: AuthViewModel) {
    val state = viewModel.stateLogin.collectAsState().value
    TabRow(
        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.01f),
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(0.5f),
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
            ),
        selectedTabIndex = state.LoginOrSignIn
    ) {
        LoginSigninTab(state,"Login",2,viewModel::updateLoginOrSignIn)
        LoginSigninTab(state,"Signup",1,viewModel::updateLoginOrSignIn)
    }
}

@Composable
private fun LoginSigninTab(
    state: LoginState,
    text:String,
    changeLoginorSignInState: Int,
    changeStateFunction:(Int)->Unit
) {
    Tab(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer.copy(
                0.5f
            ), shape = RoundedCornerShape(topEnd = 15.dp)
        ),
        selected = state.LoginOrSignIn == changeLoginorSignInState,
        onClick = { changeStateFunction(changeLoginorSignInState) },
        text = { Text(text = text, maxLines = 2, overflow = TextOverflow.Ellipsis) }
    )
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
fun LoginBox(
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