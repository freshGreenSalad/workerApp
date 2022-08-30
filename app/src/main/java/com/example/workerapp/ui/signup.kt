package com.example.workerapp.ui

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
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import com.example.workerapp.navgraphs.SettingsNavGraph
import com.example.workerapp.ui.destinations.MainHolderComposableDestination
import com.example.workerapp.ui.destinations.ProfilePageComposableDestination
import com.example.workerapp.ui.homeUi.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@SettingsNavGraph
@Destination
@Composable
fun signin(
    viewModel: MainViewModel,
    navigate: DestinationsNavigator
) {
    val scope = CoroutineScope(Dispatchers.IO)
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
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
                              //  (viewModel::postAuthProfile)(ProfileLoginAuthRequest(email.text,password.text))
                            navigate.navigate(ProfilePageComposableDestination)
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