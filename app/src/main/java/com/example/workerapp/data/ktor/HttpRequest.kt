package com.example.workerapp.data.ktor

import android.text.TextUtils
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.workerapp.data.authResult
import com.example.workerapp.data.models.Profile
import com.example.workerapp.data.models.ProfileLoginAuthRequest
import com.example.workerapp.data.models.ProfileInformation
import com.example.workerapp.data.models.jwtTokin
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject

class AWSRequest @Inject constructor(
    private val client: HttpClient,
    private val dataStore: DataStore<Preferences>
) :AWSInterface{

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
            val response = client.post(Routes.postProfileAuth) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }
            dataStore.edit { settings ->
                settings[stringPreferencesKey( name = "JWT")] = Json.decodeFromString<jwtTokin>(response.body<String>()).token.toString()
            }
            authResult.authorised()
        } catch (e: Exception) {
            authResult.unauthorised()
        }
    }
    override suspend fun getauthtokin(profileLoginAuthRequest: ProfileLoginAuthRequest): authResult<Unit> {
        return try {
            val respose = client.post(Routes.signin) {
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString<ProfileLoginAuthRequest>(profileLoginAuthRequest))
            }
            dataStore.edit { settings ->
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

    override suspend fun authenticate() {
        val jwt = dataStore.data.first()[stringPreferencesKey(name = "JWT")]
        val response = client.post(Routes.authenticate){

            headers {
                append("Authorization","Bearer $jwt")
            }
        }.body<String>()
        Log.d("authentication", response)
    }

    override suspend fun postProfileInformation(profileInformation: ProfileInformation) {
        val jwt = dataStore.data.first()[stringPreferencesKey(name = "JWT")]
        client.post(Routes.postProfileInformation) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString<ProfileInformation>(profileInformation))
            headers {
                append("Authorization", "Bearer $jwt")
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
    const val postProfileInformation = Url+"postProfileInformation"
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}