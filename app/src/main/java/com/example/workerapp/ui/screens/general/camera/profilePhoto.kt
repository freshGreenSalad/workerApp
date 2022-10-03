package com.example.workerapp.ui.screens.general.camera

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import java.util.concurrent.Executors
import coil.compose.rememberImagePainter
import java.io.File
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.workerapp.ui.CameraView

@Composable
fun ProfileCamera(
    shouldShowCamera: Boolean,
    shouldShowPhoto: Boolean,
    shouldShowPho: ()->Unit,
    shouldShowCam: ()->Unit,
    photoUri: Uri,
    updatePhotoURI: (Uri)->Unit
) {
    val mediaDir = File.createTempFile("filename", null, LocalContext.current.cacheDir)

    val cameraExecutor = Executors.newSingleThreadExecutor()

    fun handleImageCapture(uri: Uri) {
        Log.i("kilo", "Image captured: $uri")
        updatePhotoURI(uri)
        shouldShowCam()
        shouldShowPho()
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
            Text(text = "image screen")
            Image(
                painter = rememberAsyncImagePainter(photoUri),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            val file = File(photoUri.path)
            println(file.totalSpace)
            println(file.isFile)
            println(file.name)
        }
    }
}

