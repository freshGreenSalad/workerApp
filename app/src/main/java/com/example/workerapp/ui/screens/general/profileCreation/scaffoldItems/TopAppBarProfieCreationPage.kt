package com.example.workerapp.ui.screens.general.profileCreation.scaffoldItems

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TopBarProfileCreationPage(
    title: String
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
    )
}