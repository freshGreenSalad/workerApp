package com.tamaki.workerapp.ui.screens.general.signup


import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
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
import com.tamaki.workerapp.data.authResult
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.destinations.SupervisorSignupScaffoldDestination
import com.tamaki.workerapp.destinations.WorkerSignupScaffoldDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@ProfileCreationNavGraph(start = true)
@Destination
@Composable
fun Signup(
    viewModel: SignupViewModel,
    navigate: DestinationsNavigator
) {
    val scope = CoroutineScope(Dispatchers.IO)

    var email by remember { mutableStateOf(TextFieldValue("")) }

    var password by remember { mutableStateOf(TextFieldValue("")) }

    var isSupervisor by remember { mutableStateOf(true) }

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResults.collect { result ->
            when (result) {
                is authResult.authorised -> navigate.navigate(if (isSupervisor) SupervisorSignupScaffoldDestination else WorkerSignupScaffoldDestination)
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

            AnimatedContent(
                targetState = isSupervisor,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, delayMillis = 150))
                        .with(fadeOut(animationSpec = tween(150)))
                }
            ) { targetState ->
                when (targetState) {
                    true -> {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(280.dp)
                                .clickable { isSupervisor = true }
                                .background(
                                    shape = RoundedCornerShape(5.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            contentAlignment = Alignment.Center,
                        ) {Row(modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically

                            ) {
                            Text(
                                text = "Supervisor",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.background
                            )
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = ""
                            )
                        }
                        }
                    }
                    false -> {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(280.dp)
                                .background(
                                    shape = RoundedCornerShape(5.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                .clickable { isSupervisor = true },
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "Supervisor",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            AnimatedContent(
                targetState = isSupervisor,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, delayMillis = 150))
                        .with(fadeOut(animationSpec = tween(150)))
                }
            ) { targetState ->
                when (targetState) {
                    false -> {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(280.dp)
                                .clickable { isSupervisor = false }
                                .background(
                                    shape = RoundedCornerShape(5.dp),
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            contentAlignment = Alignment.Center,
                        ) {Row(modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                text = "Worker",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.background
                            )
                            Icon(
                                imageVector = Icons.Outlined.Done,
                                contentDescription = ""
                            )
                        }
                        }
                    }
                    true -> {
                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .width(280.dp)
                                .background(
                                    shape = RoundedCornerShape(5.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                )
                                .clickable { isSupervisor = false },
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "Worker",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
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
                    .height(60.dp)
                    .background(
                        shape = RoundedCornerShape(5.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    .clickable {
                        scope.launch {
                            (viewModel::updateStateEmailPassword)(email.text, password.text, isSupervisor)
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