package com.example.aibank.ui.utils

import android.util.Log
import com.example.aibank.models.ConvertJson
import com.example.aibank.models.Currency
import com.example.aibank.ui.network.ApiService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import javax.inject.Inject


class Repository @Inject constructor(private val apiService: ApiService) {


    private val auth = Firebase.auth
    fun registerUser(
        email: String,
        password: String,
        username: String,
        phoneNumber: String? = null,
        adult: Boolean? = null
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun logInUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

//    fun signOut() {
//        auth.signOut()
//    }

    suspend fun sendSMS(phoneNumber: String, sender: String, message: String) {

        apiService.requestSMS(
            phoneNumber,
            sender,
            message,
            "simple-sms-sender.p.rapidapi.com",
            "771843af12msh78120f126dfe56cp1c2776jsn2b6b34c62123"
        )
        Log.d("12345", "sendSMS: 11111")
    }

    suspend fun getCurrencies(): Resource<MutableList<Currency.CommercialRates>> {
        return try {
            val response = apiService.getCurrencyList(apikey = "66juH27XPTdaLmfW5auEY6Su492MQbgQ")
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody.commercialRatesList as MutableList<Currency.CommercialRates>)
            } else {
                Log.d("12345", "getCurrencies: ${response.message()}")
                Resource.Error(
                    response.message().toString(),
                    responseBody!!.commercialRatesList as MutableList<Currency.CommercialRates>
                )
            }
        } catch (e: Exception) {
            Log.d("12345", "getCurrencies: ${e.message}")
            Resource.Error(e.message)
        }
    }

    suspend fun getMainCurrencies(): Resource<MutableList<Currency.CommercialRates>> {
        return try {
            val response = apiService.getMainCurrencyList(
                currency = "usd,eur,gbp",
                apikey = "66juH27XPTdaLmfW5auEY6Su492MQbgQ"
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody.commercialRatesList as MutableList<Currency.CommercialRates>)
            } else {
                Resource.Error(
                    response.message().toString(),
                    responseBody!!.commercialRatesList as MutableList<Currency.CommercialRates>
                )
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun convertPreview(amount: String, from: String, to: String): ConvertJson {
        val response = apiService.convert(amount, from, to, "66juH27XPTdaLmfW5auEY6Su492MQbgQ")
        val responseBody = response.body()
        return responseBody!!
    }
}