package com.tamaki.workerapp.data.di

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_IN_REQUEST
import com.tamaki.workerapp.userPathways.signin.onetap.Constants.SIGN_UP_REQUEST
import com.tamaki.workerapp.userPathways.signin.onetap.ProfileRepositoryImpl
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.implementaion.AuthRepositoryImpl
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.AuthRepository
import com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class AppModuleer {

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOneTapClient(
        @ApplicationContext
        context: Context
    ) = Identity.getSignInClient(context)

    @Provides
    @Named(SIGN_IN_REQUEST)
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("638296576508-tmnmef59k0qgb1egv78pi0qop7ila96q.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(true)
                .build())
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named(SIGN_UP_REQUEST)
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId("638296576508-tmnmef59k0qgb1egv78pi0qop7ila96q.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("638296576508-tmnmef59k0qgb1egv78pi0qop7ila96q.apps.googleusercontent.com")
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named(SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        @Named(SIGN_UP_REQUEST)
        signUpRequest: BeginSignInRequest,
        db: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signUpRequest = signUpRequest,
        db = db
    )

    @Provides
    fun provideProfileRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        signInClient: GoogleSignInClient,
        db: FirebaseFirestore
    ): ProfileRepository = ProfileRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInClient = signInClient,
        db = db
    )
}