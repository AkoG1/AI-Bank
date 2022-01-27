package com.example.aibank.ui.others

import com.example.aibank.models.ConvertJson
import com.example.aibank.models.Currency
import com.example.aibankv10.ui.network.ApiService
import javax.inject.Inject

class CurrenciesRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun getCurrencies() : Currency {

        val response = apiService.getCurrencyList(apikey = "66juH27XPTdaLmfW5auEY6Su492MQbgQ")
        val responseBody = response.body()
        return responseBody!!

    }

    suspend fun getMainCurrencies() : Currency {

        val response = apiService.getMainCurrencyList(currency = "usd,eur,gbp", apikey = "66juH27XPTdaLmfW5auEY6Su492MQbgQ")
        val responseBody = response.body()
        return responseBody!!

    }

    suspend fun convertPreview(amount: String, from:String, to:String) : ConvertJson {

        val response = apiService.convert(amount, from, to, "66juH27XPTdaLmfW5auEY6Su492MQbgQ")
        val responseBody = response.body()
        return responseBody!!

    }

}