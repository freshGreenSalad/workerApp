package com.example.workerapp.data.ktor

import com.example.workerapp.data.models.Profile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class AWSRequest(
    private val client: HttpClient
):AWSInterface {

    override suspend fun getWorkerString(key:Int): String {
        return try {
            client.get{ url(Routes.getWorkerProfileFromS3+key.toString()) }.body()
        } catch (e: Exception) {
            return "could not load worker through ktor"
        }
    }

    override suspend fun postProfile(testProfile:Profile) {
        client.post(Routes.postProfile){
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString<Profile>(testProfile))
        }
    }
}

object Routes {
    private const val Url = "http://192.168.1.151:8080/"
    const val getWorkerProfileFromS3 = Url+"getInitalDataInCloud/WorkerPrimaryInfo/"
    const val postProfile = Url+ "PutprofileInDynamodb"
}