package com.example.workerapp.ui.screens.general.supervisorSignup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.SignupSigninViewModel
import com.example.workerapp.destinations.SupervisorSignupCameraDestination
import com.example.workerapp.destinations.SupervisorSignupScaffoldDestination
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@ProfileCreationNavGraph
@Destination
@Composable
fun MapScreen(
    navigator: DestinationsNavigator,
    viewModel: SignupSigninViewModel,
) {
    val mapState by viewModel.stateMap.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navigator.navigate(SupervisorSignupScaffoldDestination)
                }
            ) {
                Icon(imageVector = Icons.Filled.Done, contentDescription = "", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { paddingValue ->
        GoogleMap(
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