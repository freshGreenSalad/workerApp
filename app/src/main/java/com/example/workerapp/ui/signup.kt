package com.example.workerapp.ui

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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.workerapp.ui.destinations.MainHolderComposableDestination
import com.example.workerapp.ui.homeUi.MainViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Destination
@Composable
fun signin(
    viewModel: MainViewModel,
    navagate: DestinationsNavigator
){
    val scope = CoroutineScope(Dispatchers.IO)
    var workerFirstName by remember { mutableStateOf(TextFieldValue("")) }
    var workerLastName by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var randomNumber = (0..1000).random()
    var company by remember { mutableStateOf(TextFieldValue("")) }
    val workerForm = com.example.workerapp.data.models.Profile(
        "UserProfileTable",
        "Email",
        email.text,
        "workerFirstName",
        workerFirstName.text,
        "workerLastName",
        workerLastName.text,
        "Key",
        randomNumber.toString(),
        "company",
        company.text
    )
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
        Column() {
            TextField(
                value = workerFirstName,
                onValueChange = {
                    workerFirstName = it
                },
                placeholder = { Text("First Name") },
                singleLine = true,
                keyboardActions = keyboardActions
            )
            Spacer(modifier = Modifier.height(15.dp))
            TextField(
                value = workerLastName,
                onValueChange = {
                    workerLastName = it
                },
                placeholder = { Text("Last Name") },
                singleLine = true,
                keyboardActions = keyboardActions
            )
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
                value = company,
                onValueChange = {
                    company = it
                },
                placeholder = { Text("Company") },
                singleLine = true,
                keyboardActions = keyboardActions
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row() {
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Labourer",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(50.dp)
                        .background(
                            shape = RoundedCornerShape(5.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Supervisor",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
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
                        scope.launch {
                            (viewModel::postprofile)(workerForm)
                            (viewModel::upsert)(workerForm)
                        }
                        navagate.navigate(MainHolderComposableDestination)
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