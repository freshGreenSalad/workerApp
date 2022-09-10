package com.example.workerapp.data.ktor

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import coil.network.HttpException
import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import com.example.workerapp.data.models.jwtTokin
import com.example.workerapp.ui.homeUi.dataStore
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
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
    //posts the initial profile to dynamo db
    override suspend fun postProfileAuth(profileLoginAuthRequest: ProfileLoginAuthRequest):authResult<Unit> {
        return try {
            if (!profileLoginAuthRequest.email.isEmailValid()) {throw Exception ("email not valid")}
            if (profileLoginAuthRequest.password.length < 8) {throw Exception ("password to small")}
            client.post(Routes.postProfileAuth){
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }
    override suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest,context:Context): authResult<Unit> {
        return try {
            val respose = client.post(Routes.signin) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }

            context.dataStore.edit { settings ->
                settings[stringPreferencesKey( name = "JWT")] = Json.decodeFromString<jwtTokin>(respose.body<String>()).token.toString()
            }
            when (respose.status) {
                HttpStatusCode.OK -> authResult.authorised()
                HttpStatusCode.Conflict -> authResult.unauthorised()
                else -> authResult.unauthorised()
            }

        }catch(e:Exception){
            Log.d("login", "catch block of getauthtokin http  request")
            authResult.unauthorised()
        }
    }

    override suspend fun authenticate(jwt: String) {
        client.get(Routes.authenticate){
            headers {
                append("Authorization","Bearer $jwt")
            }
        }
    }
}

object Routes {
    private const val Url = "http://192.168.1.151:8080/"
    const val getWorkerProfileFromS3 = Url+"getInitalDataInCloud/WorkerPrimaryInfo/"
    const val postProfile = Url+ "PutprofileInDynamodb"
    const val postProfileAuth = Url + "UserAuthSignUp"
    const val signin = Url + "signIn"
    const val authenticate = Url + "authenticate"
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}