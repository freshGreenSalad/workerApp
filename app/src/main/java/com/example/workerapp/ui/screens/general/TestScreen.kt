package com.example.workerapp.ui.screens.general

import androidx.compose.runtime.*
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.SignupSigninViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun testScreen(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {

}


