package com.tamaki.workerapp.ui.screens.supervisor.supervisorProfile

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
import com.tamaki.workerapp.R
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.SupervisorProfile
import com.tamaki.workerapp.data.dataClasses.supervisorDataClasses.supervisorProfileFail
import com.tamaki.workerapp.data.navgraphs.HomeViewNavGraph
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.*
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.MainDrawer
import com.tamaki.workerapp.ui.screens.supervisor.supervisorHome.homeUIScafoldItems.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0


@OptIn(ExperimentalMaterial3Api::class)
@HomeViewNavGraph
@Composable
@Destination
fun SupervisorProfilePage(
    navigator: DestinationsNavigator,
    viewModel: SupervisorViewModel,
) {
    val viewState by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    val drawerState = viewState.drawerState

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
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
            val profileState = produceState(
                initialValue = supervisorProfileFail,
                producer = {
                    value = try {
                        (viewModel::getSupervisorProfile)()
                    } catch (e: Exception) {
                        supervisorProfileFail
                    }
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                ProfileImageHeader(
                    profileState.value
                )
                SupervisorProfile(
                    profileState.value
                )
            }
        }
    }
}

@Composable
fun SupervisorProfile(
    profileState: SupervisorProfile
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                Text(
                    text = "Name:" + profileState.firstName + profileState.lastName,
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
                    "Email: " + profileState.email, modifier = Modifier
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
    profileState: SupervisorProfile
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp

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
                    .size(150.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        data = profileState.personalPhoto
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

