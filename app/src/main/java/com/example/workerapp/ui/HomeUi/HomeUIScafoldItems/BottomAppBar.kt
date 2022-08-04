package com.example.workerapp.ui.HomeUi

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable



@Composable
fun BottomAppBarHomePage(
    selectedItem: HomeBottomAppBarTabs,
    onclick: (HomeBottomAppBarTabs) -> Unit,
    homeAppBarTabs: List<HomeBottomAppBarTabs>
){
    val values = homeAppBarTabs.map { it.name }
    NavigationBar(
       // containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        values.forEachIndexed { index, title ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(title) },
                selected = title == selectedItem.toString(),
                onClick = {onclick(homeAppBarTabs[index]) },
               /* colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary
                )*/
            )
        }

    }
}
