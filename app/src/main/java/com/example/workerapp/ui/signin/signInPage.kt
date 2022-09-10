package com.example.workerapp.ui.signin

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import com.example.workerapp.destinations.MainHolderComposableDestination
import com.example.workerapp.destinations.SignupDestination
import com.example.workerapp.destinations.WorkerProfileDestination
import com.example.workerapp.navgraphs.HomeViewNavGraph
import com.example.workerapp.ui.homeUi.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction2

@HomeViewNavGraph
@Destination
@Composable
fun SignInPage(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel
) {


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SignInBox(
            navigator,
            viewModel::login,
            viewModel
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInBox(
    navigator: DestinationsNavigator,
    Login: KSuspendFunction2<ProfileLoginAuthRequest, Context, Unit>,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect() { result ->
            when (result) {
                is authResult.authorised -> navigator.navigate(MainHolderComposableDestination)
                is authResult.unauthorised -> {
                    Log.d("login", "unothorised block")
                    Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
                }
                is authResult.unknownError -> {
                    Toast.makeText(context, "wrong email password combo", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Column {
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text(text = "Enter email") },
            singleLine = true,
            keyboardActions = keyboardActions
        )
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = { Text("Enter password") },
            singleLine = true,
            keyboardActions = keyboardActions
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable {
                    scope.launch {
                        Login(ProfileLoginAuthRequest(email.text, password.text), context)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable {
                    navigator.navigate(SignupDestination)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign up",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Box(
            modifier = Modifier
                .width(280.dp)
                .height(50.dp)
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = MaterialTheme.colorScheme.primary
                )
                .clickable {
                    navigator.navigate(WorkerProfileDestination)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "to working page",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}