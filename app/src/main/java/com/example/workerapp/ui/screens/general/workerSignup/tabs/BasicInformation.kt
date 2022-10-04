package com.example.workerapp.ui.screens.general.workerSignup

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.workerapp.destinations.WorkerSignupCameraDestination
import com.example.workerapp.ui.screens.general.camera.ProfileCamera
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicInformation(
    firstname: String,
    lastname: String,
    UpdateFirstname: (String) -> Unit,
    UpdateLastname: (String) -> Unit,
    photoUri: Uri,
    navigator: DestinationsNavigator
) {
    Box(modifier = Modifier.fillMaxSize()) {

        val keyboardController = LocalSoftwareKeyboardController.current

        val keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )

        LazyColumn {

            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "BasicInformation",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    TextField(
                        value = firstname,
                        onValueChange = {
                            UpdateFirstname(it)
                        },
                        placeholder = { Text("Firstname") },
                        singleLine = true,
                        keyboardActions = keyboardActions
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    TextField(
                        value = lastname,
                        onValueChange = {
                            UpdateLastname(it)
                        },
                        placeholder = { Text("Lastname") },
                        singleLine = true,
                        keyboardActions = keyboardActions
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Button(onClick = { navigator.navigate(WorkerSignupCameraDestination) })
                    {
                        Text(
                            text = "Take A Photo of Yourself!",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .shadow(5.dp)
                            .background(
                                shape = RoundedCornerShape(5.dp),
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(photoUri),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}