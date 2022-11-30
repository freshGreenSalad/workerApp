package com.tamaki.workerapp.ui

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import java.util.*

@Composable
fun HireWorker(
    paddingValues: PaddingValues,
    worker: WorkerProfile
){
    Surface(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        Column() {
            MyContent()
            HireButton()
        }
    }
}

@Composable
fun MyContent(){
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    val mDate = remember { mutableStateOf("") }
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(Color.LightGray) ) {
            Text(text = "choose start date", color = Color.White)
        }
        Text(text = "Selected Date: ${mDate.value}", textAlign = TextAlign.Center)
    }
}

@Composable
fun HireButton(){
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
            }
        ,
        contentAlignment = Alignment.Center
    ){
        Text(
            color = MaterialTheme.colorScheme.onSecondary,
            text = "Confirm",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}