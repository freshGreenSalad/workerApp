package com.example.workerapp.ui.profileCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.workerapp.data.viewModel.EmployeerOrEmployee
import com.example.workerapp.data.viewModel.signUpViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun ProfileCreationPage(
    viewModel: signUpViewModel
) {
    val viewState by viewModel.state.collectAsState()
    val selectedtab = viewState.selectedEmployeerOrEmployee
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row() {
                ButtonUserType("Employer", EmployeerOrEmployee.employer, viewModel::changeUserType)
                Spacer(modifier = Modifier.width(20.dp))
                ButtonUserType("Employee", EmployeerOrEmployee.employee, viewModel::changeUserType)
            }
            Surface(modifier = Modifier.fillMaxSize()) {
                when (selectedtab) {
                    EmployeerOrEmployee.employer -> {
                        Text(text = "employer")
                    }
                    EmployeerOrEmployee.employee -> {
                        Text(text = "Employee")
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonUserType(
    text: String,
    usertype: EmployeerOrEmployee,
    changeusertype: (EmployeerOrEmployee)-> Unit
    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(10.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                changeusertype(usertype)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.onSecondary,
            text = text,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}