package com.example.workerapp.data.ktor

import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.*

interface AWSInterface {
    suspend fun getWorkerString(key: Int): String

    suspend fun  postProfile(textprofile: Profile)

    suspend fun postProfileAuth (profileLoginAuthRequest: ProfileLoginAuthRequest)

    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): String

    suspend fun authenticate(jwt:String)

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