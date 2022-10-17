package com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tamaki.workerapp.R

@Composable
fun TopBar(
    openDrawer: () -> Unit,
    title: String
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { openDrawer() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.drawer_menu)
                )
            }
        },
    )
}

