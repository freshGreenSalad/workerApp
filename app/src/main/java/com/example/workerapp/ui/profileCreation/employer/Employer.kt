package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.workerapp.ui.profileCreation.employee.switchrow
import com.example.workerapp.ui.profileCreation.employer.basicInformationEmployer

@Composable
fun Employer(){
    var showBasicInformation by remember { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ){
        item {
            switchrow("basic information", showBasicInformation, { showBasicInformation = !showBasicInformation })
            if (showBasicInformation) { basicInformationEmployer() }
        }
    }
}
