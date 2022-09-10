package com.example.workerapp.data.ktor

import android.content.Context
import com.example.workerapp.data.authResult
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

    //posts initial profile to dynamodb db
    suspend fun postProfileAuth (profileLoginAuthRequest: ProfileLoginAuthRequest):authResult<Unit>
    //sends login details, returns authorisation and saves jwt at lower level
    suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest, context: Context): authResult<Unit>

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