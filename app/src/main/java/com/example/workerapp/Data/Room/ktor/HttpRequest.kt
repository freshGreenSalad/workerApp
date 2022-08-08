package com.example.workerapp.Data.Room.ktor

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*


class AWSRequest(
    private val client: HttpClient
):AWSInterface {

    override suspend fun gethelloWorld(): String{
        try {
            return client.get {
                url(routes.Url)
            }
        }catch (e: Exception){
            Log.d("AWS","did not Load")
            return "did not load"
        }
    }
}

object routes {
    const val Url = "http://192.168.1.151:8080/s3"
}