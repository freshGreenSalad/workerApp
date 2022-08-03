package com.example.workerapp.ui.workerInfoPage.WorkerUITabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.workerapp.ui.workerInfoPage.WorkerChipGroup

@Composable
fun WorkerStats(
    paddingValues: PaddingValues,
){
    Column() {
        Box(
            modifier = androidx.compose.ui.Modifier
                .padding(paddingValues)
                .height(200.dp)
        ) {
            anamatedWorkHistory()
        }
        WorkerChipGroup()
    }
}

@Composable
fun anamatedWorkHistory(){
    val canvasSize = 40
    val circleSize = 15
    val configuration = LocalConfiguration.current
    val count = 40
    val screenWidth = configuration.screenWidthDp.dp

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count) { index ->
            Row() {
                Canvas(modifier = Modifier
                    .size(canvasSize.dp)
                    .clickable { }, onDraw = {
                    val lineYStart : Float = if (index == 0) size.height/2f else 0f
                    val lineYEnd : Float = if (index == count-1) size.height/2f else size.height
                    drawCircle(
                        color = Color.Gray,
                        radius = circleSize.toFloat()
                    )
                    drawLine(
                        start = Offset(size.width/2f,lineYStart),
                        end = Offset(size.width/2f, lineYEnd),
                        strokeWidth = 5f,
                        color = Color.Gray
                    )
                }
                )
                Box(modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { }
                    .height(32.dp)
                    .width(screenWidth-canvasSize.dp)
                    .background(
                        color = Color.LightGray
                    )
                )
                {
                    Text(text = "this is a box")
                }
            }
        }
    }
}