package com.example.workerapp.data.navgraphs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class ProfileCreationNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = true)
@NavGraph
annotation class HomeViewNavGraph(
    val start: Boolean = false
)

@RootNavGraph
@NavGraph
annotation class WorkerNavGraph(
    val start: Boolean = false
)



@ProfileCreationNavGraph
@Destination
@Composable
fun test(){
    Text(text = "asdfg")
}