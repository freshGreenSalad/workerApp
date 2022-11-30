package com.tamaki.workerapp.userPathways.signup.supervisorSignup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.destinations.SupervisorSignupCameraDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.*

@Composable
fun SupervisorBasicInformation(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {
    val viewSupervisorStateCamera by viewModel.stateSupervisorCamera.collectAsState()
    val viewState by viewModel.stateSupervisorScaffold.collectAsState()
    val imageData = remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = (ActivityResultContracts.GetContent()),
        onResult = { uri ->
            imageData.value = uri
        }
    )
    LazyColumnOfOrganisedComposables() {
        StandardPrimaryTextHeading("Basic Information")
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions("Firstname", viewModel::updateSupervisorFirstName, viewState.supervisorFirstName)
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions("Lastname", viewModel::updateSupervisorLastName, viewState.supervisorLastName)
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton("Take A Photo of Yourself!"){navigator.navigate(SupervisorSignupCameraDestination)}
        Spacer(modifier = Modifier.height(15.dp))
        ScreenShotImageHolder(viewSupervisorStateCamera.photoUri)
        StandardButton("clicking this button takes you to your own photos",{ launcher.launch("image/jpeg") })
    }
}