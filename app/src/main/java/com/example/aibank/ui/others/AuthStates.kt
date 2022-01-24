package com.example.aibankv10.ui.others

import com.example.aibank.ui.others.User

sealed interface AuthStates {
    data class AuthSuccess(val user: User) : AuthStates
    data class Error(val message: String?) : AuthStates
    object Loading: AuthStates
    object Idle: AuthStates
}