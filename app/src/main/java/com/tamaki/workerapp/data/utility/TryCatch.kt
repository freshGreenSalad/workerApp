package com.tamaki.workerapp.data.utility

class TryCatch {
    suspend fun suspendTryCatchWrapper(function:suspend()->Unit) {
        try {  function()} catch (e: Exception) { }
    }
}