package com.tamaki.workerapp.ui.screens.general.workerSignup.experienceSubcatagory

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.StandardTextHeading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Formwork(
    viewModel: SignupViewModel
){
    val viewstate by viewModel.stateExperience.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()){
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                viewstate.formworkMap["Docka"]?.let {
                    Checkbox(
                        checked = it,
                        onCheckedChange = { (viewModel::updateFormworkMap)("Docka") })
                }
                StandardTextHeading("Docka")
            }
        }
    }
}