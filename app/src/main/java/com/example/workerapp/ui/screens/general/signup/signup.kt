package com.example.workerapp.ui.screens.general.signup


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
import com.example.workerapp.data.viewModel.signUpViewModel
import com.example.workerapp.destinations.ProfileCreationPageDestination
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ProfileCreationNavGraph
@Destination
@Composable
fun Signup(
    viewModel: signUpViewModel,
    navigate: DestinationsNavigator
) {
    val scope = CoroutineScope(Dispatchers.IO)

    var email by remember { mutableStateOf(TextFieldValue("")) }

    var password by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is authResult.authorised -> navigate.navigate(ProfileCreationPageDestination)
                is authResult.unauthorised -> {
                    Log.d("signup", "unauthorised block")
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                is authResult.unknownError -> {
                    Toast.makeText(context, "profile not created", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }

        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        val keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )
        Column {
            TextField(
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = { Text("Email") },
                singleLine = true,
                keyboardActions = keyboardActions
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                placeholder = { Text("Password") },
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
                            (viewModel::updateStateEmailPassword)(email.text, password.text)
                            (viewModel::postAuthProfile)()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}