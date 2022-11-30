package com.tamaki.workerapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun LightText(text:String) {
    Text(
        modifier = Modifier
            .padding(5.dp)
            .alpha(0.5f),
        text = "$text:",
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun LargeTransperentText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f),
        text = text,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun StandardPrimaryTextHeading(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun StandardOnPrimaryTextHeading(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun TextHeadingHomePage(text:String) {
    Text(
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.bodyLarge,
        text = text
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithKeyboardActions(
    text: String,
    function: (String)->Unit,
    textFieldValue: String
){

    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )

    TextField(
        value = textFieldValue,
        onValueChange = {
            function(it)
        },
        placeholder = { Text(text) },
        singleLine = true,
        keyboardActions = keyboardActions
    )
}

