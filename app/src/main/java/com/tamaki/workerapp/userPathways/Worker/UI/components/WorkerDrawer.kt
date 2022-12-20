package com.tamaki.workerapp.userPathways.Worker.UI.workerProfile

import com.tamaki.workerapp.R
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamaki.workerapp.destinations.*
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0

@Composable
fun WorkerDrawer(
    navigator: DestinationsNavigator,
    deleteFromDataStore: KSuspendFunction0< Unit>,
    closeDrawer: () -> Job,
    deleteAccount: KSuspendFunction0< Unit>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Worker Drawer",
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
            title = stringResource(id = R.string.drawer_item_home),
            closeDrawer = closeDrawer
        )
        DrawerItem(
            navigator = navigator,
            destination = WorkerProfilePageComposableDestination,
            title = stringResource(id = R.string.drawer_item_profile),
            closeDrawer = closeDrawer
        )
        signoutDrawerItem(
            clickable = deleteFromDataStore,
            title = "Signout",
            navigator = navigator,
            destination = AuthScreenDestination,
            closeDrawer = closeDrawer
        )
        DeleteDrawerItem(
            navigator = navigator,
            destination = AuthScreenDestination,
            title = "Delete Account",
            deleteAccount = deleteAccount,
            closeDrawer = closeDrawer
        )
    }
}

@Composable
fun DrawerItem(
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    title: String,
    closeDrawer: () -> Job
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                closeDrawer()
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
fun DeleteDrawerItem(
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    title: String,
    deleteAccount: KSuspendFunction0< Unit>,
    closeDrawer: () -> Job
) {

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
                    deleteAccount()
                }
                closeDrawer()
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
    clickable: KSuspendFunction0<Unit>,
    title: String,
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    closeDrawer: () -> Job
) {
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
                    clickable()
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

