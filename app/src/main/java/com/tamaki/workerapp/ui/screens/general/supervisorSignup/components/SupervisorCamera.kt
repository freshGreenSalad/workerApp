package com.tamaki.workerapp.ui.screens.general.supervisorSignup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.destinations.SupervisorSignupScaffoldDestination
import com.tamaki.workerapp.ui.screens.general.camera.ProfileCamera
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun SupervisorSignupCamera (
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
){
    val viewStateCamera by viewModel.stateSupervisorCamera.collectAsState()

    ProfileCamera(
        shouldShowPhoto = viewStateCamera.shouldShowPhoto,
        shouldShowCamera = viewStateCamera.shouldShowCamera,
        photoUri = viewStateCamera.photoUri,
        shouldShowPho = viewModel::shouldshowSupervisorPho,
        shouldShowCam = viewModel::shouldshowSupervisorcam,
        updatePhotoURI = viewModel::updateSupervisorPhotoURI,
        navigator = navigator,
        destination = SupervisorSignupScaffoldDestination
    )
}