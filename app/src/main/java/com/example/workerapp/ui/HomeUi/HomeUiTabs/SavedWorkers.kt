package com.example.workerapp.ui.HomeUi

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier



@Composable
fun SavedWorkers(
    paddingValues: PaddingValues,
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        Text(text = "saved workers page")
    }
}