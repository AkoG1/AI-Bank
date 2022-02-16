package com.example.aibank.ui.utils

sealed class Resource<out T>(val data: T? = null , val message: String? = null, val loading: Boolean = false) {

    class Success<T>(data: T) : Resource<T>(data = data)

    class Error<T>(message: String? = null, data: T? = null) : Resource<T>(data = data, message = message)

    object Loading: Resource<Nothing>()

    object Idle: Resource<Nothing>()

}