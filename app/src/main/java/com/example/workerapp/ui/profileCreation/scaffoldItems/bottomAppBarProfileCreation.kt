package com.example.workerapp.ui.profileCreation.scaffoldItems

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.viewModel.EmployeerOrEmployee
import com.example.workerapp.destinations.MainHolderComposableDestination
import com.example.workerapp.destinations.WorkerProfileDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun bottomAppBarComposable(
    selectedtab: EmployeerOrEmployee,
    navigator: DestinationsNavigator
) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        content = { Text("Create Profile!") },
        onClick = {
            when (selectedtab) {
                EmployeerOrEmployee.Employee -> navigator.navigate(WorkerProfileDestination)
                EmployeerOrEmployee.Employer -> navigator.navigate(MainHolderComposableDestination)
            }

        },
        colors = ButtonDefaults.buttonColors()
    )
}

