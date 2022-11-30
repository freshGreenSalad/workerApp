package com.tamaki.workerapp.ui.screens.general

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tamaki.workerapp.destinations.MapScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tamaki.workerapp.data.viewModel.SignupViewModel
import com.tamaki.workerapp.ui.components.LazyColumnOfOrganisedComposables
import com.tamaki.workerapp.ui.components.ScreenShotImageHolder
import com.tamaki.workerapp.ui.components.StandardPrimaryTextHeading
import com.tamaki.workerapp.ui.components.TextFieldWithKeyboardActions

@Composable
fun AddressScreen(
    navigator: DestinationsNavigator,
    viewModel: SignupViewModel
) {

    val mapState by viewModel.stateMap.collectAsState()

    LazyColumnOfOrganisedComposables(){
        StandardPrimaryTextHeading("Site address")
        Spacer(modifier = Modifier.height(15.dp))
        TextFieldWithKeyboardActions("Site Address", viewModel::updateSiteAddress, mapState.siteAddress)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { navigator.navigate(MapScreenDestination) }) { Text("Add button on map") }
        Spacer(modifier = Modifier.height(15.dp))
        ScreenShotImageHolder(mapState.mapScreenShoturi)
    }
}
