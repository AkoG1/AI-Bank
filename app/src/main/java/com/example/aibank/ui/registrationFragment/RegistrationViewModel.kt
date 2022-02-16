package com.example.aibank.ui.registrationFragment

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibank.ui.utils.AuthStates
import com.example.aibank.ui.utils.Repository
import com.example.aibank.ui.utils.User
import com.example.aibankv10.ui.others.uploadUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _authStateFlow = MutableStateFlow<AuthStates>(AuthStates.Idle)
    val authStateFlow get() = _authStateFlow

    suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        phoneNumber: String,
        context: Context
    ) {

        _authStateFlow.emit(AuthStates.Loading)

        repository.registerUser(email, password, username, phoneNumber).addOnSuccessListener {

            val user = User(it.user?.uid, email, password, username, phoneNumber)
            uploadUserData(it.user?.uid!!, username = username, phoneNumber, context)

            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.AuthSuccess(user))
            }

        }.addOnFailureListener {

            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.Error(it.message))
            }

        }
    }
}