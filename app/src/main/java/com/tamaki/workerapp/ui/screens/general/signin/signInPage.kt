package com.tamaki.workerapp.ui.screens.general.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.dataClasses.auth.ProfileLoginAuthRequest
import com.tamaki.workerapp.destinations.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.SigninNavGraph
import com.tamaki.workerapp.data.viewModel.SigninViewModel
import com.tamaki.workerapp.ui.components.LogoImageBox
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.ui.components.TextFieldWithKeyboardActions
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@SigninNavGraph
@Destination
@Composable
fun SignInPage(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        SignInBox(
            navigator = navigator,
            viewModel = viewModel
        )
    }
}

@Composable
fun SignInBox(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel
) {

    val viewState by viewModel.stateLogin.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result.data) {
                true -> {
                    navigator.navigate(MainHolderComposableDestination)
                }
                false -> {
                    navigator.navigate(WorkerProfileDestination)
                }
                null -> {}
            }
            when (result) {
                is authResult.unauthorised -> {
                    Log.d("login", "unauthorised block")
                    Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
                }
                is authResult.unknownError -> {
                    Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }
    }

    Column {
        LogoImageBox()
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions(text = "Enter email", function = viewModel::updateEmailField , textFieldValue = viewState.email)
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions(text = "Enter password", function = viewModel::updatePasswordField , textFieldValue = viewState.password)
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton(text = "Sign up",function = viewModel::tryToLoginToAccountWhenClickingOnButton)
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton(text = "Sign up",function = {navigator.navigate(SignupDestination)})
        Spacer(modifier = Modifier.height(15.dp))
    }
}