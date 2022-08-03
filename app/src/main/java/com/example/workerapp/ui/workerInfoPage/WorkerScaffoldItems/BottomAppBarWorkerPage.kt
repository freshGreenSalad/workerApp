package com.example.workerapp.ui.workerInfoPage.WorkerScaffoldItems

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomAppBarWorkerPage(
    selectedItem: BottomAppBarWorkerPageCatagory,
    onclick: (BottomAppBarWorkerPageCatagory) -> Unit
){
    val values = BottomAppBarWorkerPageCatagory.values().map { it.name }
    NavigationBar() {
        values.forEachIndexed(){index, title ->
            NavigationBarItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                label = { Text(title) },
                selected = title == selectedItem.toString(),
                onClick = {onclick(BottomAppBarWorkerPageCatagory.values()[index]) }
            )
        }
    }
}
