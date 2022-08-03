package com.example.workerapp.ui.HomeUi


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.R
import com.example.workerapp.ui.destinations.WorkerPageDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker: Workers,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit
){
    var cornerCutt = if (showPlus) 0 else 40

    Surface(
        onClick = {
                  navigator.navigate(
                      WorkerPageDestination(
                          worker,
                      )
                  )
        },
        enabled = true,
        shadowElevation = 1000.dp,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 100.dp,
        modifier = Modifier
            .padding(8.dp)
            .clip(CutCornerShape(topEnd = cornerCutt.dp))
            .clip(RoundedCornerShape(15.dp))
            .size(width = 180.dp, height = 180.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().zIndex(1f),
            horizontalArrangement = Arrangement.End
        ) {
            watchlistedCardIcon(showPlus) { onClickWatchlist}
        }
        Image(
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = worker.image),
            contentDescription = ""
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = worker.name)
            Text(text = worker.price.toString())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun watchlistedCardIcon(
        cornerClipped:Boolean,
        onClickWatchlist : () -> Unit
){
    Box(
        modifier = Modifier
            .width(20.dp)
            .height(20.dp)
            .background(color = Color.Gray)
            .clickable {
                onClickWatchlist
            }
    ) {
        when (cornerClipped) {
           (cornerClipped== true) -> {
                   Image(
                       painter = painterResource(id = R.drawable.ic_plus),
                       contentDescription = ""
                   )
               }
            (cornerClipped == false) ->{
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = ""
                )
            }
           }
        }
}