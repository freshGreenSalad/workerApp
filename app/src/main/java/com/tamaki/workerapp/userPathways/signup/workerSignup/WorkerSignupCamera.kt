package com.tamaki.workerapp.userPathways.signup.workerSignup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.destinations.WorkerSignupScaffoldDestination
import com.tamaki.workerapp.ui.components.camera.ProfileCamera
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupCamera (
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
){
    val state by viewModel.stateLogin.collectAsState()

    ProfileCamera(
        shouldShowPhoto = state.shouldShowPhoto,
        shouldShowCamera = state.shouldShowCamera,
        photoUri = state.photoUri,
        shouldShowPho = viewModel::shouldshowPho,
        shouldShowCam = viewModel::shouldshowcam,
        updatePhotoURI = viewModel::updatePhotoURI,
        navigator = navigator,
        destination = WorkerSignupScaffoldDestination
    )
}

