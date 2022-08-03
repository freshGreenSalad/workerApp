package com.example.workerapp.ui.HomeUi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable



@Composable
fun BottomAppBarHomePage(
    selectedItem: HomeBottomAppBarTabs,
    onclick: (HomeBottomAppBarTabs) -> Unit
){
    val values = HomeBottomAppBarTabs.values().map { it.name }
    NavigationBar() {
        values.forEachIndexed(){index, title ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(title) },
                selected = title == selectedItem.toString(),
                onClick = {onclick(HomeBottomAppBarTabs.values()[index]) }
            )
        }
    }
}
