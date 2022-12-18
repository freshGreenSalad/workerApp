package com.tamaki.workerapp.data.utility

import android.net.Uri
import com.tamaki.workerapp.data.dataClasses.Email
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class jsonclientfunctions {

    suspend fun SendJsonViaRouteReturnBody(client: HttpClient, json: String, Route:String): HttpResponse {
        return client.post(Route) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun SendJsonViaRoute(client: HttpClient, json: String, Route:String,) {
        client.post(Route) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
    }

    suspend fun SendJsonViaRouteReturnHttpStatus(client: HttpClient, json: String, Route:String,):HttpStatusCode {
        return client.post(Route) {
                contentType(ContentType.Application.Json)
                setBody(json)
            }.status

    }

    suspend fun getHttpResponseWithRawEmail(client: HttpClient, Route: String, email: String, jwt: String?): String {
        val response = client.post(Route) {
            contentType(ContentType.Application.Json)
            setBody(
                Json.encodeToString(Email(email))
            )
            bearerAuth(jwt!!)
        }
        return response.body()
    }

    suspend fun getPresignLink(purposeOfImage:String, jwt:String, client: HttpClient) = try {
        client.put(Routes.presignPutRequest) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(purposeOfImage))
            bearerAuth(jwt)
        }.body()
    } catch (e: Exception) { "" }

    suspend fun uploadfileToS3(client: HttpClient, uri: Uri, presign: String) {
        val file = File(uri.path!!)
        val bodydata = file.readBytes()
        client.put(presign) {
            contentType(ContentType.MultiPart.FormData)
            setBody(bodydata)
        }
    }
}