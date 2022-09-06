package com.example.workerapp.data

sealed class authResult<T>(val data:T? = null){
    class authorised <T>(data:T? = null): authResult<T>(data)
    class unauthorised <T>: authResult<T>()
    class unknownError <T>: authResult<T>()
}
