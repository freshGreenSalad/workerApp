package com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUiTabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WorkerSearch(
    paddingValues: PaddingValues,
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        Text(text = "worker search page")
    }
}

