package com.example.workerapp.ui.screens.worker.workerProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.workerapp.R
import com.example.workerapp.data.dataClasses.workerDataClasses.WorkerProfile
import com.example.workerapp.data.dataClasses.workerDataClasses.workerProfileFail
import com.example.workerapp.data.viewModel.WorkerViewModel
import com.example.workerapp.data.navgraphs.WorkerNavGraph
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.MainDrawer
import com.example.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0


@OptIn(ExperimentalMaterial3Api::class)
@WorkerNavGraph
@Composable
@Destination
fun WorkerProfilePageComposable(
    navigator: DestinationsNavigator,
    viewModel: WorkerViewModel,
) {
    val viewState by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val drawerState = viewState.drawerState

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            WorkerDrawer(
                navigator,
                viewModel::deleteAllFromDataStore,
                closeDrawer = { scope.launch { drawerState.close() } },
                deleteAccount = viewModel::deleteAccount
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = "Profile",
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                ProfileImageHeader(
                    viewModel::getWorkerProfile
                )
                SupervisorProfile(
                    viewModel::getWorkerProfile
                )
            }
        }
    }
}

@Composable
fun SupervisorProfile(
    getWorkerProfile: KSuspendFunction0<WorkerProfile>
) {
    val profileState = produceState(
        initialValue = workerProfileFail,
        producer = {
            value = try {
                getWorkerProfile()
            } catch (e: Exception) {
                workerProfileFail
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                Text(
                    text = "Name:" + profileState.value.firstName + profileState.value.lastName,
                    modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Role:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Email: " + profileState.value.email, modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Phone:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Company: ", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Password:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}

@Composable
fun ProfileImageHeader(
    getWorkerProfile: KSuspendFunction0<WorkerProfile>
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

    val profileState = produceState(
        initialValue = workerProfileFail,
        producer = {
            value = try {
                getWorkerProfile()
            } catch (e: Exception) {
                workerProfileFail
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .shadow(1.dp)
    ) {
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
                        data = profileState.value.personalPhoto
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