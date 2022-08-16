package com.example.workerapp.Data.Room.ktor

import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.Workers
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

interface AWSInterface {

    suspend fun getWorker(key: Int): Workers

    suspend fun getImage(): String

    suspend fun getWorkerString(key:Int): String

    companion object {
        fun create(): AWSInterface {
            return AWSRequest(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(ContentNegotiation) {
                        json(
                            Json
                            {
                                prettyPrint = true
                                isLenient = true
                            }
                        )
                    }
                }
            )
        }
    }
}