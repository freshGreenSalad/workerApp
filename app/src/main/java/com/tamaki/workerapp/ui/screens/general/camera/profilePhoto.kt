package com.tamaki.workerapp.ui.screens.general.camera

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import java.util.concurrent.Executors
import java.io.File
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.tamaki.workerapp.destinations.DirectionDestination
import com.tamaki.workerapp.ui.CameraView
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProfileCamera(
    shouldShowCamera: Boolean,
    shouldShowPhoto: Boolean,
    photoUri: Uri,
    shouldShowCam: (Boolean) -> Unit,
    shouldShowPho: (Boolean) -> Unit,
    updatePhotoURI: (Uri) -> Unit,
    navigator: DestinationsNavigator,
    destination: DirectionDestination
) {
    val mediaDir = File.createTempFile("filename", null, LocalContext.current.cacheDir)

    val cameraExecutor = Executors.newSingleThreadExecutor()

    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        updatePhotoURI(uri)
        shouldShowCam(false)
        shouldShowPho(true)
    }

    if (shouldShowCamera) {
        CameraView(
            outputDirectory = mediaDir,
            executor = cameraExecutor,
            onImageCaptured = ::handleImageCapture,
            onError = { Log.e("kilo", "View error:", it) }
        )
    }

    if (shouldShowPhoto) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = rememberAsyncImagePainter(photoUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Button(onClick = { navigator.navigate(direction = destination) }) {
                    Text(
                        text = "Confirm",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

