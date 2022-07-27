package com.example.workerapp.ui.theme
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.example.workerapp.ui.HomeUi.BottomAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workerapp.Data.worker
import com.example.workerapp.ui.HomeUi.Workercard
import com.example.workerapp.ui.HomeUi.topBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination(start = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val drawerState = rememberDrawerState(initialValue = Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { Column(){
            Text(text = "drawer")
        } }
    ) {
        Scaffold(
            modifier = Modifier.padding(.4.dp),
            topBar = {
                topBar(
                    openDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar()
            },
            content = {
                main(it,navigator)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun main(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator
){
    LazyColumn(modifier = Modifier.padding(paddingValues)){
        item { 
            Text(text = "Your workers")
        }
        item{
            val items = worker
            LazyRow() {
                items(items.size) { index ->
                    Workercard(items[index],navigator)
                }
            }
        }
        item {
            Text(text = "Find New Workers")
        }
        item{
            val items = worker
            LazyRow() {
                items(items.size) { index ->
                    Workercard(items[index],navigator)
                }
            }
        }
        item {
            Text(text = "Notices")
        }
        items(3) { index ->
            Card(modifier = Modifier.padding(8.dp).fillMaxWidth().height(100.dp),
                content ={ Text(text = "$index")}
            )
        }

    }
}


