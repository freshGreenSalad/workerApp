package com.tamaki.workerapp.userPathways.signin.onetap.repositiory.interfaces

import com.tamaki.workerapp.userPathways.signin.onetap.Response

typealias SignOutResponse = Response<Boolean>
typealias RevokeAccessResponse = Response<Boolean>

interface ProfileRepository {
    val displayName: String
    val photoUrl: String

    suspend fun signOut(): SignOutResponse

    suspend fun revokeAccess(): RevokeAccessResponse
}