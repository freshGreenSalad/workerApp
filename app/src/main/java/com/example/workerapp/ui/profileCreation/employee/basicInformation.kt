package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun basicInformation() {
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var LastName by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )
    Column(modifier = Modifier.fillMaxWidth()){
        OutlinedTextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = { Text(text = "FirstName") },
            singleLine = true,
            keyboardActions = keyboardActions
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = LastName,
            onValueChange = {
                LastName = it
            },
            label = { Text(text = "LastName") },
            singleLine = true,
            keyboardActions = keyboardActions
        )
    }
}