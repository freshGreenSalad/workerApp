package com.tamaki.workerapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.tamaki.workerapp.R
import com.tamaki.workerapp.destinations.SignInPageDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.navgraphs.SigninNavGraph
import com.tamaki.workerapp.data.viewModel.SigninViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SigninNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    viewModel: SigninViewModel
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            (viewModel::changeScaleOfSplashScreenImage)()
        }
        delay(2000L)
        navigator.navigate(SignInPageDestination)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(R.drawable.crane),
            contentDescription = "logo",
            modifier = Modifier.scale(viewModel.stateLogin.collectAsState().value.scale.value)
        )
    }
}