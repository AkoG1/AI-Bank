package com.example.aibankv10.ui.others

import android.util.Log
import com.example.aibankv10.ui.network.ApiService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject



class Repository @Inject constructor(private val apiService: ApiService) {


    private val auth = Firebase.auth
    fun registerUser(email: String, password: String, username: String, phoneNumber: String? = null, adult: Boolean? = null): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun logInUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signout() {
        auth.signOut()
    }

    suspend fun sendSMS( phoneNumber: String, sender: String, message: String) {

        apiService.requestSMS(phoneNumber,sender, message, "simple-sms-sender.p.rapidapi.com", "771843af12msh78120f126dfe56cp1c2776jsn2b6b34c62123")
        Log.d("12345", "sendSMS: 11111")
    }


}