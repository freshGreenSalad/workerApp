package com.example.workerapp.ui.screens.general

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.viewModel.MapDataClass
import com.example.workerapp.destinations.MapScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddressScreen(
    navigator: DestinationsNavigator,
    map: MapDataClass,
    siteAddress: String,
    updateSiteAddress: (String) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )
    LazyColumn() {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Site address",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextField(
                    value = siteAddress,
                    onValueChange = {
                        updateSiteAddress(it)
                    },
                    placeholder = { Text("Site Address") },
                    singleLine = true,
                    keyboardActions = keyboardActions
                )
                Spacer(modifier = Modifier.height(15.dp))

                Spacer(modifier = Modifier.height(15.dp))
                Button(onClick = { navigator.navigate(MapScreenDestination) }) {
                    Text("Add button on map")
                }
            }
        }
    }
}