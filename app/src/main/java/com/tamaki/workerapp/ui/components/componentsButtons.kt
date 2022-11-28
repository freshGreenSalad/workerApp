package com.tamaki.workerapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.R

@Composable
fun StandardButton(
    text: String,
    function: ()->Unit
) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(50.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.primary
            )
            .clickable {
                function
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun LogoImageBox(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(280.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.crane),
            contentDescription = "logo",
            modifier = Modifier.scale(2.5f)
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextFieldWithKeyboardActions(
    text: String,
    function: (TextFieldValue)->Unit,
    textFieldValue: TextFieldValue
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
