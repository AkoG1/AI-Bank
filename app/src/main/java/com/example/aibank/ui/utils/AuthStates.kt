package com.example.aibank.ui.utils

sealed interface AuthStates {
    data class AuthSuccess(val user: User) : AuthStates
    data class Error(val message: String?) : AuthStates
    object Loading: AuthStates
    object Idle: AuthStates
}