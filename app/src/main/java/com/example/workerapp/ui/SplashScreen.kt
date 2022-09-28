package com.example.workerapp.ui

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.example.workerapp.R
import com.example.workerapp.destinations.SignInPageDestination
import com.example.workerapp.navgraphs.HomeViewNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

import kotlinx.coroutines.delay

@HomeViewNavGraph(start = true)
@Destination
@Composable
fun spalshScreen(
    navigator: DestinationsNavigator,
){
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navigator.navigate(SignInPageDestination)
    }
    Box(
        contentAlignment = Alignment.Center,
    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)) {
        Image(
            painter = painterResource(R.drawable.crane),
            contentDescription = "logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}