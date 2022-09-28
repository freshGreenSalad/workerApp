package com.example.workerapp.ui.screens.worker.workerProfile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workerapp.R
import com.example.workerapp.destinations.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

@Composable
fun WorkerDrawer(
    navigator: DestinationsNavigator,
    deleteFromDataStore: KSuspendFunction1<Context, Unit>,
    closeDrawer: () -> Job
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Menu",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        DrawerItem(
            navigator = navigator,
            destination = WorkerProfileDestination,
            title = stringResource(id = R.string.drawer_item_home)
        )
        DrawerItem(
            navigator = navigator,
            destination = WorkerProfilePageComposableDestination,
            title = stringResource(id = R.string.drawer_item_profile)
        )
        signoutDrawerItem(
            clickable = deleteFromDataStore,
            title = "Signout",
            navigator = navigator,
            destination = SignInPageDestination,
            closeDrawer = closeDrawer
        )
    }
}

@Composable
fun DrawerItem(
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                navigator.navigate(destination)
            }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            style = MaterialTheme.typography.bodyMedium,

            )
    }
}

@Composable
fun signoutDrawerItem(
    clickable: KSuspendFunction1<Context, Unit>,
    title: String,
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    closeDrawer: () -> Job
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                scope.launch {
                    clickable(context)
                }
                navigator.navigate(destination)
                closeDrawer()
            }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            style = MaterialTheme.typography.bodyMedium,

            )
    }
}

