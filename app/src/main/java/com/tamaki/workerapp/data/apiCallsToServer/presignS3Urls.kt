package com.tamaki.workerapp.data.apiCallsToServer

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class preSignS3Urls(
    private val client: HttpClient,
    private val jwt: String
) {

    suspend fun getPresignLink() = try {
        client.put(Routes.presignPutRequest) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString("profilePicture"))
            bearerAuth(jwt)
        }.body()
    } catch (e: Exception) {
        ""
    }
}