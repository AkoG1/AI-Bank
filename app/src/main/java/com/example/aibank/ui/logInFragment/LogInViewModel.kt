package com.example.aibankv10.ui.logInFragment


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibankv10.ui.others.AuthStates
import com.example.aibankv10.ui.others.Repository
import com.example.aibank.ui.others.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repository: Repository) : ViewModel(){

    private val _authStateFlow = MutableStateFlow<AuthStates>(AuthStates.Idle)
    val authStateFlow get() = _authStateFlow


    suspend fun logInUser(email: String, password: String) {
        Log.d("authstateflow", "${authStateFlow.value} ")
        _authStateFlow.emit(AuthStates.Loading)
        Log.d("authstateflow", "${authStateFlow.value} ")
        repository.logInUser(email, password).addOnSuccessListener {
            val user = it.user
            val user1 = User(user?.uid!!)
            //user.uidit momak bazidan info
            Log.d("userparams", "logInUser: $user ")
            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.AuthSuccess(user1))
                Log.d("authstateflow", "${authStateFlow.value} ")
            }
            Log.d("login", "logInUser: success")
            Log.d("login", "logInUser: ")
        }.addOnFailureListener {
            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.Error(it.message.toString()))
                Log.d("authstateflow", "${authStateFlow.value} ")
            }

                    Log.d("login", "logInUser: failure")
        }
    }
    
}