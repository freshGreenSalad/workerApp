package com.example.workerapp.ui.HomeUi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
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
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    )
}

