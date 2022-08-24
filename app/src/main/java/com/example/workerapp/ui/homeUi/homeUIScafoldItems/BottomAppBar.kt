package com.example.workerapp.ui.homeUi.homeUIScafoldItems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.example.workerapp.ui.homeUi.HomeBottomAppBarTabs


@Composable
fun BottomAppBarHomePage(
    selectedItem: HomeBottomAppBarTabs,
    onclick: (HomeBottomAppBarTabs) -> Unit,
    homeAppBarTabs: List<HomeBottomAppBarTabs>,
    ListOfSavedWorkers: MutableList<Int>
) {
    val titles = homeAppBarTabs.map { it.name }
    NavigationBar()
    {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = titles[0]
                )
            },
            label = { Text(titles[0]) },
            selected = titles[0] == selectedItem.toString(),
            onClick = { onclick(homeAppBarTabs[0]) },
        )
        NavigationBarItem(
            icon = {
                if (ListOfSavedWorkers.size > 0) {
                    BadgedBox(
                        badge = {
                            Badge(
                                content = { Text(ListOfSavedWorkers.size.toString()) },
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = titles[1]
                            )
                        }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = titles[1]
                    )
                }
            },
            label = { Text(titles[1]) },
            selected = titles[1] == selectedItem.toString(),
            onClick = { onclick(homeAppBarTabs[1]) },
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = titles[2]
                )
            },
            label = { Text(titles[2]) },
            selected = titles[2] == selectedItem.toString(),
            onClick = { onclick(homeAppBarTabs[2]) },
        )
    }
}
