package com.tamaki.workerapp.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.destinations.SupervisorHomeScaffoldDestination
import com.tamaki.workerapp.ui.components.StandardButton
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel
import com.tamaki.workerapp.userPathways.Supervisor.supervisorDataClasses.SuccessStatus
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HireWorker(
    viewModel: SupervisorViewModel,
    navigator: DestinationsNavigator
){
    val scope = rememberCoroutineScope()

    val state = viewModel.state.collectAsState()

    val context = LocalContext.current

    suspend fun ReadHireSuccessResultSendToastAndNavigate() {
        viewModel.HireChannelStatus.collect { result ->
            when (result) {
                is SuccessStatus.Success -> {
                    Toast.makeText(context, "${state.value.currentSelectedWorker.firstName} Successfully Hired", Toast.LENGTH_LONG).show()
                    navigator.navigate(SupervisorHomeScaffoldDestination)
                }
                is SuccessStatus.Failure -> {
                    Toast.makeText(context, "${state.value.currentSelectedWorker.firstName} not Hired", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        MyContent(viewModel)
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton("Hire",{
            (viewModel::hireWorker)()
            scope.launch {
                ReadHireSuccessResultSendToastAndNavigate()
            }
        })
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun MyContent(
    viewModel: SupervisorViewModel
){
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            (viewModel::updateChosenDate)(mDayOfMonth,mMonth,mYear)
        },
        mYear,
        mMonth,
        mDay
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton("choose start date", {mDatePickerDialog.show()} )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = state.date.toString())
    }
}