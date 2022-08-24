package com.example.workerapp.ui

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.io.File
import java.util.concurrent.ExecutorService
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.concurrent.Executors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter

@Composable
fun profilecamera(
    shouldShowCamera: Boolean,
    showCamera: ()->Unit
) {
    var shouldShowPhoto by remember { mutableStateOf(false)}
    var photoUri: Uri = Uri.EMPTY
    lateinit var cameraExecutor: ExecutorService

    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        showCamera()
        photoUri = uri
        shouldShowPhoto = false
    }

    cameraExecutor = Executors.newSingleThreadExecutor()
    if (shouldShowCamera) {
        CameraView(
            executor = cameraExecutor,
            onImageCaptured = ::handleImageCapture,
            onError = { Log.e("kilo", "View error:", it) }
        )
    }

    if (shouldShowPhoto) {
        Image(
            painter = rememberAsyncImagePainter(photoUri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        Text("adf")
    }




}

