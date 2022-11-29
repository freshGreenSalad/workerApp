package com.tamaki.workerapp.ui.screens.general.signin

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.destinations.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.SigninNavGraph
import com.tamaki.workerapp.data.viewModel.SigninViewModel
import com.tamaki.workerapp.ui.components.*

@SigninNavGraph
@Destination
@Composable
fun SignInPage(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel
) {
    ComposableThatSetsInitialScreenParameters(
        composable = {
            SignInBox(
                navigator = navigator,
                viewModel = viewModel
            )
        }
    )
}

@Composable
fun SignInBox(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel
) {

    val viewState by viewModel.stateLogin.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        (viewModel::returnAToastOrANavigationPathwayDependingOnLoginDetails)(navigator, viewModel, context)
    }

    Column {
        LogoImageBox()
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions(
            text = "Enter email",
            function = viewModel::updateEmailField,
            textFieldValue = viewState.email
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions(
            text = "Enter password",
            function = viewModel::updatePasswordField,
            textFieldValue = viewState.password
        )
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton(
            text = "Login",
            function = viewModel::tryToLoginToAccountWhenClickingOnButton
        )
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton(text = "Sign up", function = { navigator.navigate(SignupDestination) })
        Spacer(modifier = Modifier.height(15.dp))
    }
}