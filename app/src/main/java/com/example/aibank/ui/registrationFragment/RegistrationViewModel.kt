package com.example.aibankv10.ui.registrationFragment

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aibankv10.ui.others.AuthStates
import com.example.aibankv10.ui.others.Repository
import com.example.aibank.ui.others.User
import com.example.aibankv10.ui.others.uploadUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _authStateFlow = MutableStateFlow<AuthStates>(AuthStates.Idle)
    val authStateFlow get() = _authStateFlow

    suspend fun registerUser(email: String, password: String, username: String, phoneNumber:String, context: Context) {

        _authStateFlow.emit(AuthStates.Loading)

        repository.registerUser(email, password, username, phoneNumber).addOnSuccessListener {

            val user = User(it.user?.uid,email, password, username, phoneNumber)
            uploadUserData(it.user?.uid!!, username = username, phoneNumber, context)

            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.AuthSuccess(user))
            }

        }.addOnFailureListener{

            viewModelScope.launch {
                _authStateFlow.emit(AuthStates.Error(it.message))
            }

        }
    }

    private val _sms = MutableStateFlow<Int>(0)
    val sms get() = _sms

    suspend fun sendSMS(phoneNumber: String) {

        val randomNumber = (1000..9999).random()
        _sms.emit(randomNumber)
        repository.sendSMS(phoneNumber, "AI Bank", "Verification Code : $randomNumber")
        Log.d("12345", "sendSMS: 111")

    }

}




































//

//
//    suspend fun requestSMS(phoneNumber: String){
//        val randomNumber = (1000..9999).random()
//        repository.sendSMS("AI Bank", message = "Verify Code : $randomNumber", phoneNumber = phoneNumber)
//        _sms.emit(randomNumber)
//    }
//
//    fun checkSMS(){
//
//    }


