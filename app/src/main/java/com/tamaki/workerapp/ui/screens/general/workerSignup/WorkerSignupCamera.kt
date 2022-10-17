package com.tamaki.workerapp.ui.screens.general.workerSignup

import com.tamaki.workerapp.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupSigninViewModel
import com.tamaki.workerapp.destinations.WorkerSignupScaffoldDestination
import com.tamaki.workerapp.ui.screens.general.camera.ProfileCamera
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupCamera (
    navigator: DestinationsNavigator,
    viewModel: SignupSigninViewModel
){
    val viewStateCamera by viewModel.stateCamera.collectAsState()

    ProfileCamera(
        shouldShowPhoto = viewStateCamera.shouldShowPhoto,
        shouldShowCamera = viewStateCamera.shouldShowCamera,
        photoUri = viewStateCamera.photoUri,
        shouldShowPho = viewModel::shouldshowPho,
        shouldShowCam = viewModel::shouldshowcam,
        updatePhotoURI = viewModel::updatePhotoURI,
        navigator = navigator,
        destination = WorkerSignupScaffoldDestination
    )
}

