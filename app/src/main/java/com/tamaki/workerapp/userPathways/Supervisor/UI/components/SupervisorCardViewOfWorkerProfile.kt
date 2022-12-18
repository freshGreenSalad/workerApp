package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tamaki.workerapp.R
import com.tamaki.workerapp.userPathways.Worker.workerDataClasses.WorkerProfile
import com.tamaki.workerapp.ui.theme.customShapes.TriangleShape
import com.tamaki.workerapp.ui.theme.customShapes.TriangleShapeRounded
import com.tamaki.workerapp.ui.theme.customShapes.WorkerCardShape
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.destinations.WorkerPageDestination
import com.tamaki.workerapp.ui.components.SqareImageLoader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerCard(
    worker: WorkerProfile,
    navigator: DestinationsNavigator,
    viewModel:SupervisorViewModel
) {
    val inWatchlist = (viewModel::WorkerInWatchlist)(worker.email)
    Surface(
        onClick = {
            (viewModel::setCurrentSelectedWorker)(worker)
            navigator.navigate(WorkerPageDestination(worker,))
                  },
        shape = if (inWatchlist) { WorkerCardShape(40f) } else { RoundedCornerShape(15.dp) },
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(8.dp)
            .size(width = 180.dp, height = 180.dp),
        shadowElevation = 20.dp,
    ) {
        WatchlistcardIconRow(worker, inWatchlist,viewModel)
        SqareImageLoader(worker.personalPhoto,R.drawable.four)
        WorkerNameAndRateWithBackgroundShading(worker)
    }
}

@Composable
private fun WatchlistcardIconRow(
    worker: WorkerProfile,
    inWatchlist: Boolean,
    viewModel:SupervisorViewModel
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
            viewModel = viewModel
        )
    }
}

@Composable
private fun WorkerNameAndRateWithBackgroundShading(worker: WorkerProfile) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            BackgroundShading()
            WorkerNameAndRate(worker)
        }
    }
}

@Composable
private fun BackgroundShading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.75f)
            .height(40.dp)
            .background(color = MaterialTheme.colorScheme.background)
    )
}

@Composable
private fun WorkerNameAndRate(worker: WorkerProfile) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = worker.firstName)
        Text(text = if (worker.rate == 0) "" else worker.rate.toString())
    }
}

@Composable
fun WatchlistedCardIcon(
    worker: WorkerProfile,
    inWatchlist: Boolean,
    viewModel: SupervisorViewModel
) {
    if (!inWatchlist) {
        WatchlistTriangleStates({ (viewModel::addToWatchList)(worker.email)} ,TriangleShape(),Icons.Filled.CheckCircle)
    } else {
        WatchlistTriangleStates({ (viewModel::removeFromWatchlist)(worker.email)} ,TriangleShapeRounded(40f),Icons.Filled.Close)
    }
}

@Composable
private fun WatchlistTriangleStates(function: () -> Unit, shape: Shape, icon: ImageVector) {
    Box(
        modifier = Modifier
            .width(47.dp)
            .height(47.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = shape
            )
            .clickable { function() },
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            modifier = Modifier.padding(2.dp),
            imageVector = icon,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.background
        )
    }
}
