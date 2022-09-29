package com.example.workerapp.ui.screens.general.workerSignup

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workerapp.data.navgraphs.ProfileCreationNavGraph
import com.example.workerapp.data.viewModel.SignupSigninViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ProfileCreationNavGraph
@Destination
@Composable
fun WorkerSignupScaffold(
    viewModel: SignupSigninViewModel,
    navigator: DestinationsNavigator
) {
    val viewState by viewModel.state.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        workerProfileScaffold(
            navigator = navigator,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun workerProfileScaffold(
    navigator: DestinationsNavigator,
) {
    Scaffold(
        modifier = Modifier.padding(.4.dp),
        topBar = {},
        bottomBar = {},
        content = {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    )
}

enum class WorkerSignUpPoint(){
    basicinformation, Experience, tickets
}