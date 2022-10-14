package com.example.workerapp.ui.screens.general.workerSignup.experienceSubcatagory

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formwork(
    FormworkMap: Map<String, Boolean>,
    updateFormworkMap:(String)->Unit
){
    LazyColumn(modifier = Modifier.fillMaxSize()){
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                FormworkMap["Docka"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { updateFormworkMap("Docka") })
                }
                Text(
                    text = "Docka",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}