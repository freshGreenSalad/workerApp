package com.tamaki.workerapp.ui.screens.general

import com.tamaki.workerapp.R
import androidx.compose.runtime.*
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupSigninViewModel
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


