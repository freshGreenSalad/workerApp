package com.example.workerapp.ui.screens.general.profileCreation.scaffoldItems

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.viewModel.EmployeerOrEmployee
import com.example.workerapp.destinations.MainHolderComposableDestination
import com.example.workerapp.destinations.WorkerProfileDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@Composable
fun bottomAppBarComposable(
    selectedtab: EmployeerOrEmployee,
    navigator: DestinationsNavigator,
    postProfileInformation: KSuspendFunction0<Unit>
) {
    val scope = rememberCoroutineScope()
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        content = { Text("Create Profile!") },
        onClick = {
            scope.launch {
                postProfileInformation()
            }
            when (selectedtab) {
                EmployeerOrEmployee.Employee -> navigator.navigate(WorkerProfileDestination)
                EmployeerOrEmployee.Employer -> navigator.navigate(MainHolderComposableDestination)
            }

        },
        colors = ButtonDefaults.buttonColors()
    )
}

