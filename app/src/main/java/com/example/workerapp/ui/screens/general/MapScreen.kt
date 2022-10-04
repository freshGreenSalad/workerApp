package com.example.workerapp.ui.screens.general

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.workerapp.data.viewModel.MapDataClass
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapScreen(
    map: MapDataClass
){
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = map.properties,
        uiSettings = MapUiSettings()
    )
}