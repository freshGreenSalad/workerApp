package com.example.workerapp.Data.Room.ktor

import android.util.Log
import io.ktor.client.*
import io.ktor.client.request.*


class AWSRequest(
    private val client: HttpClient
):AWSInterface {

    override suspend fun getWorker(key:Int): String{
        try {
            return client.get {
                url(routes.gets3 + key.toString())
            }
        }catch (e: Exception){
            Log.d("AWS","did not Load")
            return "did not load"
        }

    }
    override suspend fun getImage(): String{
        try {
            return client.get {
                url(routes.getImage)
            }
        }catch (e: Exception){
            Log.d("AWS","did not Load")
            return "did not load"
        }
    }


}

object routes {
    private const val Url = "http://192.168.1.151:8080/"
    const val s3 = Url+"s3"
    const val gets3 = Url+"getInitalDataInCloud/WorkerPrimaryInfo/"
    const val getImage = Url+"GetImageS3"
}