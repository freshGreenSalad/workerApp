package com.example.workerapp.Data.Room.ktor

import androidx.compose.ui.viewinterop.AndroidView
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.logging.*

interface AWSInterface {

    suspend fun gethelloWorld(): String

    companion object {
        fun create(): AWSInterface {
            return AWSRequest(
                    client = HttpClient(Android){
                        install(Logging){
                            level = LogLevel.ALL
                        }
                    }
            )
        }
    }
}