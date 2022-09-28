package com.example.workerapp.ui.screens.general.profileCreation.employer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun basicInformationEmployer() {
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var LastName by remember { mutableStateOf(TextFieldValue("")) }
    var company by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )
    Column(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = company,
            onValueChange = {
                company = it
            },
            label = { Text(text = "Company") },
            singleLine = true,
            keyboardActions = keyboardActions
        )
    }
}