package com.example.workerapp.ui.profileCreation.scaffoldItems

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun bottomAppBarComposable(){
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        content = {Text("Create Profile!")},
        onClick = {},
        colors = ButtonDefaults.buttonColors()
    )
}

