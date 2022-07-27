package com.example.workerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.workerapp.ui.NavGraphs
import com.example.workerapp.ui.theme.WorkerAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkerAppTheme {
              DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}

