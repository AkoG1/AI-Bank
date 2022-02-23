package com.example.aibank.repository

import android.util.Log
import com.example.aibank.models.ConvertModel
import com.example.aibank.models.CryptoDataItem
import com.example.aibank.models.Currency
import com.example.aibank.ui.network.ApiService
import com.example.aibank.ui.network.CryptoApiService
import com.example.aibank.ui.utils.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import javax.inject.Inject


class Repository @Inject constructor(
    private val apiService: ApiService,
    private val cryptoApiService: CryptoApiService
) {


    private val auth = Firebase.auth
    fun registerUser(
        email: String,
        password: String,
    ): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun logInUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    suspend fun getCurrencies(): Resource<MutableList<Currency.CommercialRates>> {
        return try {
            val response = apiService.getCurrencyList(apikey = API_KEY_TBC)
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

    suspend fun getMainCurrencies(): Resource<MutableList<Currency.CommercialRates>> {
        return try {
            val response = apiService.getMainCurrencyList(
                currency = MAIN_CURRENCIES,
                apikey = API_KEY_TBC
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

    suspend fun convertPreview(amount: String, from: String, to: String): ConvertModel {
        val response = apiService.convert(amount, from, to, API_KEY_TBC)
        val responseBody = response.body()
        return responseBody!!
    }

    suspend fun getCryptos(): Resource<MutableList<CryptoDataItem>> {
        return try {
            val response = cryptoApiService.getData(USD, MARKET_CAP_DESC, FIFTY, false)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message().toString(), responseBody)
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    suspend fun getCryptoById(id: String): Resource<MutableList<CryptoDataItem>> {
        return try {
            val response = cryptoApiService.getDataById(USD, id, MARKET_CAP_DESC, FIFTY, false)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                Resource.Success(responseBody)
            } else {
                Resource.Error(response.message().toString())
            }
        } catch (e: Exception) {
            Resource.Error(e.message)
        }
    }

    companion object {
        private const val API_KEY_TBC = "66juH27XPTdaLmfW5auEY6Su492MQbgQ"
        private const val USD = "usd"
        private const val MARKET_CAP_DESC = "market_cap_desc"
        private const val FIFTY = 50
        private const val MAIN_CURRENCIES = "usd,eur,gbp"
    }
}