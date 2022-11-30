package com.tamaki.workerapp.ui.screens.general

import androidx.compose.runtime.*
import com.tamaki.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun testScreen(
    viewModel: SignupViewModel,
    navigator: DestinationsNavigator
) {


}


