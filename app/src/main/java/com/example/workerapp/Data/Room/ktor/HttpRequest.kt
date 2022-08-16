package com.example.workerapp.Data.Room.ktor

import android.util.Log
import com.example.workerapp.Data.Room.WorkerTest
import com.example.workerapp.Data.Room.Workers
import com.example.workerapp.Data.Room.workerTestTest
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


class AWSRequest(
    private val client: HttpClient
):AWSInterface {

    override suspend fun getWorker(key:Int): Workers {
        return try {
            val worker: Workers = client.get("http://192.168.1.151:8080/getInitalDataInCloud/WorkerPrimaryInfo/1").body<Workers>()
            return worker
        } catch (e: Exception) {
            Log.d("AWS", "did not Load")
            val worker7 = Json.decodeFromString<Workers>("""{"id":1,"name":"John","age":25,"image":1,"price":40}""")
            return worker7
        }
    }

    override suspend fun getWorkerString(key:Int): String {
        return try {
            client.get{ url(routes.gets3+key.toString()) }.body()
        } catch (e: Exception) {
            Log.d("AWS", "did not Load")
            val worker7 = "could not get string"
            return worker7
        }
    }


    override suspend fun getImage(): String{
        return try {
            client.get {
                url(routes.getImage)
            }.body()
        }catch (e: Exception){
            Log.d("AWS","did not Load")
            "did not load"
        }
    }


}
object routes {
    private const val Url = "http://192.168.1.151:8080/"
    const val s3 = Url+"s3"
    const val gets3 = Url+"getInitalDataInCloud/WorkerPrimaryInfo/"
    const val getImage = Url+"GetImageS3"
}