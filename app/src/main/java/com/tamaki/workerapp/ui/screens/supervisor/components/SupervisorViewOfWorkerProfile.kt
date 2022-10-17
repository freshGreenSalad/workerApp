package com.tamaki.workerapp.ui.screens.supervisor.supervisorHome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.ui.theme.customShapes.TriangleShape
import com.tamaki.workerapp.ui.theme.customShapes.TriangleShapeRounded
import com.tamaki.workerapp.ui.theme.customShapes.WorkerCardShape
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker: WorkerProfile,
    navigator: DestinationsNavigator,
    watchlistedWorkers: MutableList<String>,
    removeFromWatchlist: (String) -> Unit,
    addToWatchList: (String) -> Unit
) {
    val inWatchlist = true // TODO: change this to a check for in watchlist
    Surface(
        onClick = {
            /*navigator.navigate(
                WorkerPageDestination(
                    worker.value,
                )
            )*/
        },
        shape = if (inWatchlist) {
            WorkerCardShape(40f)
        } else {
            RoundedCornerShape(15.dp)
        },
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
            WatchlistedCardIcon(
                worker = worker,
                inWatchlist = inWatchlist,
                removeFromWatchlist = removeFromWatchlist,
                addToWatchList = addToWatchList
            )
        }
        AsyncImage(
            modifier = Modifier.size(200.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    worker.personalPhoto
                )
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.four),
            contentScale = ContentScale.FillBounds,
            contentDescription = ""
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    //.background(color = MaterialTheme.colorScheme.background)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.75f)
                        .height(40.dp)
                        .background(color = MaterialTheme.colorScheme.background)
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = worker.firstName)
                    Text(text = if (worker.rate == 0) "" else worker.rate.toString())
                }
            }
        }
    }
}

@Composable
fun WatchlistedCardIcon(
    worker: WorkerProfile,
    inWatchlist: Boolean,
    removeFromWatchlist: (String) -> Unit,
    addToWatchList: (String) -> Unit
) {
    if (!inWatchlist) {
        Box(
            modifier = Modifier
                .width(47.dp)
                .height(47.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = TriangleShape()
                )
                .clickable {
                    addToWatchList(worker.email)
                },
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = stringResource(id = R.string.worker_card_addToWatchlist),
                tint = MaterialTheme.colorScheme.background
            )
        }
    } else {
        Box(
            modifier = Modifier
                .width(47.dp)
                .height(47.dp)
                .clip(
                    TriangleShapeRounded(40f)
                )
                .background(color = MaterialTheme.colorScheme.primary)
                .clickable {
                    removeFromWatchlist(worker.email)
                },
            contentAlignment = Alignment.BottomStart
        ) {
            Icon(
                modifier = Modifier.padding(2.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(id = R.string.worker_card_addToWatchlist),
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
}
