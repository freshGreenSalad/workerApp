package com.example.workerapp.ui.screens.general.workerSignup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BasicInformation() {
    Box(modifier = Modifier.fillMaxSize()) {

        var firstname by remember { mutableStateOf("") }

        var lastname by remember { mutableStateOf("") }

        val keyboardController = LocalSoftwareKeyboardController.current
        val keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
            }
        )

        LazyColumn {
            item { Text("BasicInformation") }
            item {
                TextField(
                    value = firstname,
                    onValueChange = {
                        firstname = it
                    },
                    placeholder = { Text("Email") },
                    singleLine = true,
                    keyboardActions = keyboardActions
                )
                TextField(
                    value = lastname,
                    onValueChange = {
                        lastname = it
                    },
                    placeholder = { Text("Email") },
                    singleLine = true,
                    keyboardActions = keyboardActions
                )
                Box(modifier = Modifier.width(280.dp).height(50.dp).clickable {

                }){
                    Text(
                        text = "Take Photo of Yourself",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                }
            }
        }
    }
}