package com.tamaki.workerapp.userPathways.signin.onetap

import android.content.ContentValues.TAG
import android.util.Log

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ): Response<T>()

    data class Failure(
        val e: Exception
    ): Response<Nothing>()
}

class Utils {
    companion object {
        fun print(e: Exception) {
            Log.e(TAG, e.message ?: e.toString())
        }
    }
}