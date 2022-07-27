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
import com.example.workerapp.ui.HomeUi.topBar
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
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
                main(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun main(
    paddingValues: PaddingValues
){
    LazyColumn(modifier = Modifier.padding(paddingValues)){
        item { 
            Text(text = "Your workers")
        }
        item{
            LazyRow() {
                items(10) { index ->
                    Card(modifier = Modifier.padding(8.dp).size(width = 120.dp, height = 180.dp),
                        content = { Text(text = "$index")}
                    ) 
                }
            }
        }
        item {
            Text(text = "Find New Workers")
        }
        item{
            LazyRow() {
                items(10) { index ->
                    Card(modifier = Modifier.padding(8.dp).size(width = 120.dp, height = 180.dp),
                        content = { Text(text = "$index")}
                    )
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


