package com.tamaki.workerapp.userPathways.signin.onetap.repositiory.implementaion

import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.CREATED_AT
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.DISPLAY_NAME
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.EMAIL
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.PHOTO_URL
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_IN_REQUEST
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_UP_REQUEST
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.USERS
import com.tamaki.workerapp.userPathways.signin.onetap.Response
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.AuthRepository
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.OneTapSignInResponse
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.SignInWithGoogleResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl  @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named(SIGN_IN_REQUEST)
    private var signInRequest: BeginSignInRequest,
    @Named(SIGN_UP_REQUEST)
    private var signUpRequest: BeginSignInRequest,
    private val db: FirebaseFirestore
) : AuthRepository {
    override val isUserAuthenticatedInFirebase = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle(): OneTapSignInResponse {
        return try {
            val signInResult = oneTapClient.beginSignIn(signInRequest).await()
            Response.Success(signInResult)
        } catch (e: Exception) {
            try {
                val signUpResult = oneTapClient.beginSignIn(signUpRequest).await()
                Response.Success(signUpResult)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun firebaseSignInWithGoogle(
        googleCredential: AuthCredential
    ): SignInWithGoogleResponse {
        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                addUserToFirestore()
            }
            printuserdetails()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private suspend fun addUserToFirestore() {
        auth.currentUser?.apply {
            val user = toUser()
            db.collection(USERS).document(uid).set(user).await()
        }
    }

    private suspend fun printuserdetails() {
        auth.currentUser?.apply {
            val user = toUser()
            Log.d("user", user.toString())
        }
    }
}

fun FirebaseUser.toUser() = mapOf(
    DISPLAY_NAME to displayName,
    EMAIL to email,
    PHOTO_URL to photoUrl?.toString(),
    CREATED_AT to serverTimestamp()
)