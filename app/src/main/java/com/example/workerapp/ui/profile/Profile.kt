package com.example.workerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.workerapp.R
import com.example.workerapp.ui.homeUi.*
import com.example.workerapp.ui.homeUi.homeUIScafoldItems.MainDrawer
import com.example.workerapp.ui.homeUi.homeUIScafoldItems.TopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination
fun Profile(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = hiltViewModel(),
) {
    var shouldShowCamera by remember { mutableStateOf(false) }
    val viewState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val drawerState = viewState.drawerState
    fun showCamera() {
        shouldShowCamera = !shouldShowCamera
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            MainDrawer(
                navigator
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
            if (shouldShowCamera) {
                profilecamera(shouldShowCamera, { showCamera() })
            } else {
                profile(it, { showCamera() })
            }
        }
    }
}

@Composable
fun profile(
    padding: PaddingValues,
    showCamera: () -> Unit
) {
    Column(
        modifier = Modifier.padding(padding)
    ) {
        ProfileImageHeader(
            showCamera = showCamera
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                Text(
                    "Name:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.7f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Role:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Email:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Phone:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    "Company:", modifier = Modifier
                        .padding(5.dp)
                        .alpha(.5f), style = MaterialTheme.typography.titleLarge
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
    showCamera: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .shadow(1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {}
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
                        "https://testbucketletshopeitsfree.s3.ap-southeast-2.amazonaws.com/WorkerImages/" + "1"
                    )
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.four),
                contentScale = ContentScale.FillBounds,
                contentDescription = ""
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier.clickable {
                    showCamera()
                },
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "",
            )

        }
    }
}

