package com.example.workerapp.data.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

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
}

object Routes {
    private const val Url = "http://192.168.1.151:8080/"
    const val getWorkerProfileFromS3 = Url+"getInitalDataInCloud/WorkerPrimaryInfo/"
}