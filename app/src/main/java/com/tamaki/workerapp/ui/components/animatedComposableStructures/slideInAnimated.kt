package com.tamaki.workerapp.ui.components.animatedComposableStructures

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WrapperAnimateSlideInFromRight(composable: @Composable() () -> Unit) {

    val scope = rememberCoroutineScope()
    var showBox by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        scope.launch {
            delay(2000L)
            showBox = true
        }
    }

    AnimatedVisibility(
        visible = showBox,
        enter = slideInHorizontally(),
    ) {
        composable()
    }
}