package com.example.workerapp.ui.HomeUi


import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.R
import com.example.workerapp.ui.destinations.WorkerPageDestination
import com.example.workerapp.ui.theme.TriangleShape
import com.example.workerapp.ui.theme.WorkerCardShape
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Workercard(
    worker: State<WorkerTest>,
    navigator: DestinationsNavigator,
    showPlus: Boolean,
    onClickWatchlist: () -> Unit,
    watchlistedWorkers: MutableList<Int>,
    removeFromWatchlist: (Int) -> Unit ,
    addToWatchList:(Int) -> Unit
) {

    val savedWorkers = mutableListOf<Int>(1,2)

    val onClickWatchlist4 = fun(key:Int){
        savedWorkers.remove(key)
        Log.d("removed a worker","removed a worker")
    }

    val onClickWatchlist5 = fun(key:Int){
        savedWorkers.add(key)
        Log.d("added a worker", "added a worker")
    }
    val inWatchlist = worker.value.key !in savedWorkers
    val imageURL = worker.value.imageURL
    Surface(
        onClick = {
            navigator.navigate(
                WorkerPageDestination(
                    5,
                    worker.value
                )
            )
        },
        shape = if (!inWatchlist) {
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
            watchlistedCardIcon(worker,inWatchlist, { onClickWatchlist() }, onClickWatchlist4, onClickWatchlist5)
        }
        AsyncImage(
                modifier = Modifier.size(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        "https://testbucketletshopeitsfree.s3.ap-southeast-2.amazonaws.com/WorkerImages/"+imageURL
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
            Text(text = worker.value.name)
            Text(text = if (worker.value.hourlyRate == null)"" else worker.value.hourlyRate.toString())
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun watchlistedCardIcon(
    worker: State<WorkerTest>,
    cornerClipped: Boolean,
    onClickWatchlist: () -> Unit,
    removeFromWatchlist: (Int) -> Unit ,
    addToWatchList:(Int) -> Unit
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
                    addToWatchList(worker.value.key)
                    Log.d("log test", "test")
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
                    removeFromWatchlist(worker.value.key)

                },
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
            )
        }
    }
}
