package com.tamaki.workerapp.ui.screens.general.supervisorSignup.components

import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.graphics.applyCanvas
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.GoogleMap
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupSigninViewModel
import com.tamaki.workerapp.destinations.SupervisorSignupScaffoldDestination
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@ProfileCreationNavGraph
@Destination
@Composable
fun MapScreen(
    navigator: DestinationsNavigator,
    viewModel: SignupSigninViewModel,
) {
    val mediaDir = File.createTempFile("filename", null, LocalContext.current.cacheDir)
    val view = LocalView.current
    val context = LocalContext.current

    val handler = Handler(Looper.getMainLooper())
    fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
    val mapState by viewModel.stateMap.collectAsState()
    val returnUri = viewModel::returnUri

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                   /* handler.postDelayed(Runnable {
                        val bmp = Bitmap.createBitmap(view.width, view.height,
                            Bitmap.Config.ARGB_8888).applyCanvas {
                            view.draw(this)
                        }
                        bmp.let {
                            File(mediaDir, "screenshot.png").writeBitmap(bmp, Bitmap.CompressFormat.PNG, 85)
                            returnUri(Uri.fromFile(mediaDir))
                        }
                    }, 1000)*/
                    navigator.navigate(SupervisorSignupScaffoldDestination)
                }
            ) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValue ->
        val map = GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            cameraPositionState = CameraPositionState(
                position = CameraPosition(LatLng(-36.86, 174.83), 12f, 0f, 0f)
            ),
            onMapLongClick = { (viewModel::updateSitelatlng)(it) },
            properties = mapState.map.properties,
            uiSettings = MapUiSettings(
                mapToolbarEnabled = true,
                compassEnabled = true,
                indoorLevelPickerEnabled = true,
                myLocationButtonEnabled = true,
                rotationGesturesEnabled = false,
                scrollGesturesEnabled = true,
                tiltGesturesEnabled = true,
                zoomControlsEnabled = false,
                zoomGesturesEnabled = true,
                scrollGesturesEnabledDuringRotateOrZoom = true
            )
        ) {
            Marker(
                state = mapState.latLngAddress,
                title = "Your Site",
                snippet = "Marker on Your Site"
            )
        }
    }
}