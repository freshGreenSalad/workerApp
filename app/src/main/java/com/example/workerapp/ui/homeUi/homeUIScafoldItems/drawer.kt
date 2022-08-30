package com.example.workerapp.ui.homeUi.homeUIScafoldItems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workerapp.R
import com.example.workerapp.ui.destinations.DirectionDestination
import com.example.workerapp.ui.destinations.MainHolderComposableDestination
import com.example.workerapp.ui.destinations.ProfilePageComposableDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun MainDrawer(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Menu",
            modifier = Modifier.padding(10.dp),
            style = MaterialTheme.typography.bodyMedium
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        DrawerItem(
            navigator = navigator,
            destination = MainHolderComposableDestination,
            title = stringResource(id = R.string.drawer_item_home)
        )
        DrawerItem(
            navigator = navigator,
            destination = ProfilePageComposableDestination,
            title = stringResource(id = R.string.drawer_item_profile)
        )
    }
}

@Composable
fun DrawerItem(
    navigator: DestinationsNavigator,
    destination: DirectionDestination,
    title: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                navigator.navigate(destination)
            }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            style = MaterialTheme.typography.bodyMedium,

            )
    }
}
