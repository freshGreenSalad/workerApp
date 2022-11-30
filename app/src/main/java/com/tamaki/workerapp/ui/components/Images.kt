package com.tamaki.workerapp.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.tamaki.workerapp.R

@Composable
fun LogoImageBox(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(280.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.crane),
            contentDescription = "logo",
            modifier = Modifier.scale(2.5f)
        )
    }
}

@Composable
fun ScreenShotImageHolder(uri: Uri) {
    Box(
        modifier = Modifier
            .size(300.dp)
            .shadow(5.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun SqareImageLoader(imageString :String, imageholder:Int) {
    AsyncImage(
        modifier = Modifier.size(200.dp),
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageString)
            .crossfade(true)
            .build(),
        placeholder = painterResource(imageholder),
        contentScale = ContentScale.FillBounds,
        contentDescription = ""
    )
}