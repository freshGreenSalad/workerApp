package com.example.workerapp.ui.workerInfoPage.workerUITabs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.workerapp.data.models.Worker
import com.example.workerapp.R
import com.example.workerapp.ui.workerInfoPage.WorkerChipGroup

@Composable
fun WorkerPhotoTab(
    paddingValues: PaddingValues,
    worker: Worker,
    workerhistory: List<Pair<Int,String>>,
    workerSkill: List<String>,
    workerTools: List<String>
){
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        ProfileImageHeaderWithoutCamera(
            worker = worker
        )
        WorkerStats(
            worker = worker,
            workerhistory = workerhistory,
            workerSkill = workerSkill,
            workerTools = workerTools
        )
    }
}

@Composable
fun ProfileImageHeaderWithoutCamera(
    worker: Worker
) {

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .shadow(1.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
            ) {}
            Surface(
                shadowElevation = 5.dp,
                shape = CircleShape,
                modifier = Modifier
                    .size(150.dp)
                    .offset(
                        x = (screenWidth - 150.dp) / 2,
                        y = 40.dp
                    )

            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            "https://testbucketletshopeitsfree.s3.ap-southeast-2.amazonaws.com/WorkerImages/" + worker.imageURL
                        )
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.four),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = ""
                )
        }
    }
}

@Composable
fun WorkerStats(
    workerhistory: List<Pair<Int,String>>,
    worker: Worker,
    workerSkill: List<String>,
    workerTools: List<String>
){
    LazyColumn(
    ) {item {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "${worker.name}'s Work History"
        )
        Divider(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .height(2.dp)
        )
        Box(
            modifier = Modifier
                .height(200.dp)
        ) {
            anamatedWorkHistory(workerhistory)
        }
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "${worker.name}'s Skills"
        )
        Divider(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .height(2.dp)
        )
        WorkerChipGroup(workerSkill)
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "${worker.name}'s Tools"
        )
        Divider(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .height(2.dp)
        )
        cardHoldingHorizontalPager()
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = "${worker.name}'s Tools"
        )
        Divider(
            modifier = Modifier
                .padding(end = 30.dp)
                .fillMaxWidth()
                .height(2.dp)
        )
        WorkerChipGroup(workerTools)
    }
    }
}

@Composable
fun anamatedWorkHistory(workerhistory: List<Pair<Int,String>>){
    val canvasSize = 40
    val circleSize = 15
    val configuration = LocalConfiguration.current
    val count = workerhistory.size
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
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
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
                    Text(text = workerhistory[index].second)
                }
            }
        }
    }
}