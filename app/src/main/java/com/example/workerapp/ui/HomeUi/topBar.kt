package com.example.workerapp.ui.HomeUi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun topBar(
    openDrawer: () -> Unit
)
{
    CenterAlignedTopAppBar(
        title = { Text("Centered TopAppBar") },
        navigationIcon = {
            IconButton(onClick = { openDrawer() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

