package com.tamaki.workerapp.userPathways.Worker.UI.workerProfile

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.dataClasses.JobSite
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun WorkerHomeProfile(
    paddingValues: PaddingValues,
    navagator: DestinationsNavigator
) {
    val jobSite = JobSite("ardmore watercare treatment plant","8 ardmore road","03/10/22","Geoff de vries")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Your Stats"
            )
        }
        item {
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            WorkerHomeProfileLazyRow(navagator = navagator)
        }
        item {
            Text(
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodyLarge,
                text = "Current Job"
            )
        }
        item {
            Divider(
                modifier = Modifier
                    .padding(end = 30.dp)
                    .fillMaxWidth()
                    .height(2.dp)
            )
        }
        item{
            CurrentJob(jobSite)
        }
    }
}

@Composable
fun CurrentJob(
    jobSite:JobSite
) {
    Surface(
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.tertiaryContainer,
        modifier = Modifier
            .padding(
                start = 8.dp,
                top = 8.dp,
                bottom = 8.dp,
                end = 8.dp

            )
            .size(width = 400.dp, height = 180.dp)
            .clickable {

            },
        shadowElevation = 20.dp,
    ) {
        Column() {
            Text(
                text = jobSite.name,
                modifier = Modifier.padding(4.dp).alpha(0.5f),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = jobSite.supervisor,
                modifier = Modifier.padding(4.dp).alpha(0.5f),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = jobSite.address,
                modifier = Modifier.padding(4.dp).alpha(0.5f),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun WorkerHomeProfileLazyRow(
    navagator: DestinationsNavigator
) {
    LazyRow() {
        item() {
            workerStarIndicator(navagator, 4)
        }
        item {
            workerTimelynessIndicator(navagator, 90)
        }
        item{
            workerErningsIndicator(navagator,95)
        }
        item {
            anotherIndicator(navagator,95)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun workerStarIndicator(
    navigator: DestinationsNavigator,
    rating: Int
) {
    val iconColor = MaterialTheme.colorScheme.secondary
    val painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_star))
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 90f
        color = iconColor.toArgb()
    }
    Surface(
        onClick = {
            //  navigator.navigate()
        },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = (54f * 5f),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = iconColor,
                    startAngle = 135f,
                    sweepAngle = (54f * rating.toFloat()),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )
                drawContext.canvas.nativeCanvas.drawText(
                    "${rating.toString()}",
                    center.x,
                    center.y + 180f,
                    paint
                )
                withTransform(
                    {
                        transform(Matrix().apply {
                            scale(1f, 1f)
                            translate(120f, 120f)
                        }
                        )
                    }
                ) {
                    with(painter) {
                        draw(
                            painter.intrinsicSize,
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun workerTimelynessIndicator(
    navigator: DestinationsNavigator,
    timelyness: Int
) {
    val iconColor = MaterialTheme.colorScheme.secondary
    val painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_time))
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 90f
        color = iconColor.toArgb()
    }
    Surface(
        onClick = {
            //  navigator.navigate()
        },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = (2.7f * 100f),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = iconColor,
                    startAngle = 135f,
                    sweepAngle = (2.7f * timelyness.toFloat()),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    "${timelyness.toString()}",
                    center.x,
                    center.y + 180f,
                    paint
                )
                withTransform(
                    {
                        transform(Matrix().apply {
                            scale(1f, 1f)
                            translate(120f, 120f)
                        }
                        )
                    }
                ) {
                    with(painter) {
                        draw(
                            painter.intrinsicSize,
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun workerErningsIndicator(
    navigator: DestinationsNavigator,
    earnings: Int
) {
    val iconColor = MaterialTheme.colorScheme.secondary
    val painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payments))
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 90f
        color = iconColor.toArgb()
    }
    Surface(
        onClick = {
            //  navigator.navigate()
        },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = (2.7f * 100f),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = iconColor,
                    startAngle = 135f,
                    sweepAngle = (2.7f * earnings.toFloat()),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    earnings.toString(),
                    center.x,
                    center.y + 180f,
                    paint
                )
                withTransform(
                    {
                        transform(Matrix().apply {
                            scale(1f, 1f)
                            translate(120f, 120f)
                        }
                        )
                    }
                ) {
                    with(painter) {
                        draw(
                            painter.intrinsicSize,
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun anotherIndicator(
    navigator: DestinationsNavigator,
    earnings: Int
) {
    val iconColor = MaterialTheme.colorScheme.secondary
    val painter = rememberVectorPainter(image = ImageVector.vectorResource(id = R.drawable.ic_payments))
    val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 90f
        color = iconColor.toArgb()
    }
    Surface(
        onClick = {
            //  navigator.navigate()
        },
        shape = RoundedCornerShape(15.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                drawArc(
                    color = Color.LightGray,
                    startAngle = 135f,
                    sweepAngle = (2.7f * 100f),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )
                drawArc(
                    color = iconColor,
                    startAngle = 135f,
                    sweepAngle = (2.7f * earnings.toFloat()),
                    useCenter = false,
                    topLeft = Offset(((size.width) / 2f) - 180f, ((size.height) / 2f) - 160f),
                    size = Size(350f, 350f),
                    style = Stroke(20f, cap = StrokeCap.Round)
                )

                drawContext.canvas.nativeCanvas.drawText(
                    earnings.toString(),
                    center.x,
                    center.y + 180f,
                    paint
                )
                withTransform(
                    {
                        transform(Matrix().apply {
                            scale(1f, 1f)
                            translate(120f, 120f)
                        }
                        )
                    }
                ) {
                    with(painter) {
                        draw(
                            painter.intrinsicSize,
                            colorFilter = ColorFilter.tint(iconColor)
                        )
                    }
                }
            }
        }
    }

}