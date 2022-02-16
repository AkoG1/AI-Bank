package com.example.aibank.ui.logInFragment


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibank.ui.utils.AuthStates
import com.example.aibank.repository.Repository
import com.example.aibank.ui.utils.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _authStateFlow = MutableStateFlow<AuthStates>(AuthStates.Idle)
    val authStateFlow get() = _authStateFlow


    suspend fun logInUser(email: String, password: String) {
        _authStateFlow.emit(AuthStates.Loading)
        repository.logInUser(email, password).addOnSuccessListener {
            val user = it.user
            val user1 = User(user?.uid!!)
            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.AuthSuccess(user1))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.Error(it.message.toString()))
            }
        }
    }

}