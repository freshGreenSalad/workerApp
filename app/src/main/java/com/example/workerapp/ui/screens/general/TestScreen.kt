package com.example.workerapp.ui.screens.general

import androidx.compose.runtime.*
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.SignupSigninViewModel
import com.example.workerapp.ui.screens.general.camera.ProfileCamera
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun testScreen(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {
    val viewStateCamera by viewModel.stateCamera.collectAsState()

    /*ProfileCamera(
        shouldShowCamera = viewStateCamera.shouldShowCamera,
        shouldShowPhoto = viewStateCamera.shouldShowPhoto,
        shouldShowCam = viewModel::shouldshowcam,
        shouldShowPho = viewModel::shouldshowPho,
        photoUri = viewStateCamera.photoUri,
        updatePhotoURI = viewModel::updatePhotoURI,
    )*/
}
