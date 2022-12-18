package com.tamaki.workerapp.userPathways.signup.workerSignup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.destinations.WorkerSignupCameraDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.*

@Composable
fun BasicInformation(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {
    val imageData = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = (ActivityResultContracts.GetContent()),
        onResult = { uri ->
            imageData.value = uri
        }
    )

    val State by viewModel.stateLogin.collectAsState()

    LazyColumnOfOrganisedComposables() {
        StandardPrimaryTextHeading("Basic Information")
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions("Firstname",viewModel::updateFirstname,State.firstname)
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions("Lastname",viewModel::updateLastname,State.lastname)
        Spacer(modifier = Modifier.height(15.dp))
        StandardButton("Take A Photo of Yourself!", { navigator.navigate(WorkerSignupCameraDestination) })
        Spacer(modifier = Modifier.height(15.dp))
        ScreenShotImageHolder(State.photoUri)
        StandardButton("clicking this button takes you to your own photos", { launcher.launch("image/jpeg") })
    }
}