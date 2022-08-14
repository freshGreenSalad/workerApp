package com.example.workerapp.ui.HomeUi


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
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
import com.example.workerapp.ui.theme.TriangleShape
import com.example.workerapp.ui.theme.WorkerCardShape
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker: Workers,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit
){
    Surface(
        onClick = {
                  navigator.navigate(
                      WorkerPageDestination(
                          worker,
                      )
                  )
        },
        shape = if (!showPlus) { WorkerCardShape(40f) } else { RoundedCornerShape(15.dp) },
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1f),
            horizontalArrangement = Arrangement.End
        ) {
            watchlistedCardIcon(showPlus, { onClickWatchlist() })
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
    cornerClipped: Boolean,
    onClickWatchlist: () -> Unit
) {
    if (cornerClipped) {
        Box(
            modifier = Modifier
                .width(47.dp)
                .height(47.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = TriangleShape()
                )
                .clickable {
                    onClickWatchlist()
                },
            contentAlignment = Alignment.TopEnd
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = ""
            )

        }
    } else {

        Box(
            modifier = Modifier
                .width(47.dp)
                .height(47.dp)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 15.dp
                    )
                )
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable {
                    onClickWatchlist()
                },
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
              //  modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
               // alignment = Alignment.BottomStart
            )
        }
    }
}
