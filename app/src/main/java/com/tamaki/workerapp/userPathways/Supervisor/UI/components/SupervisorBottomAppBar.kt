package com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.homeUIScafoldItems

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import com.tamaki.workerapp.userPathways.Supervisor.UI.supervisorHome.SupervisorViewModel


@Composable
fun BottomAppBarHomePage(
    viewmodel:SupervisorViewModel
) {
    val state by viewmodel.state.collectAsState()
    val titles = state.homeAppBarTabs.map { it.name }
    NavigationBar()
    {
        SupervisorBottomAppBarNavItem(viewmodel,0,{IconBottomAppBar(Icons.Filled.Home, titles, 0)})
        SupervisorBottomAppBarNavItem(viewmodel,1,{HeartIconwithcounter(viewmodel)})
        SupervisorBottomAppBarNavItem(viewmodel,2,{IconBottomAppBar(Icons.Filled.Search, titles, 2)})
    }
}

@Composable
private fun HeartIconwithcounter(viewmodel: SupervisorViewModel) {
    val state by viewmodel.state.collectAsState()
    val titles = state.homeAppBarTabs.map { it.name }
    if (state.savedWorkers.size > 0) {
        BadgedBox(
            badge = {
                Badge(
                    content = { Text(state.savedWorkers.size.toString()) },
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
        Icon(imageVector = Icons.Filled.Favorite, contentDescription = titles[1])
    }
}

@Composable
private fun RowScope.SupervisorBottomAppBarNavItem( viewmodel: SupervisorViewModel, num:Int, icon:@Composable ()->Unit) {
    val state by viewmodel.state.collectAsState()
    val titles = state.homeAppBarTabs.map { it.name }
    NavigationBarItem(
        icon = {},
        label = { Text(titles[num]) },
        selected = titles[num] == state.selectedHomeBarTab.name,
        onClick = { (viewmodel::onClickHomeBottomAppTab)(state.homeAppBarTabs[num]) },
    )
}

@Composable
private fun IconBottomAppBar(icon: ImageVector, titles: List<String>, num: Int) {
    Icon(
        imageVector = icon,
        contentDescription = titles[num]
    )
}
