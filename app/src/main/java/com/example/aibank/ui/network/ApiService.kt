package com.example.aibank.ui.network


import com.example.aibank.models.ConvertJson
import com.example.aibank.models.Currency
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("{number}/{sender}/{message}")
    suspend fun requestSMS(
        @Path("number") phoneNumber: String,
        @Path("sender") sender: String,
        @Path("message") message: String,
        @Header("x-rapidapi-host") xrapidapihost: String,
        @Header("x-rapidapi-key") apikey: String
    )

    @GET("exchange-rates/commercial")
    suspend fun getCurrencyList(@Header("apikey") apikey: String): Response<Currency>

    @GET("exchange-rates/commercial")
    suspend fun getMainCurrencyList(
        @Query("currency") currency: String,
        @Header("apikey") apikey: String
    ): Response<Currency>

    @GET("exchange-rates/commercial/convert")
    suspend fun convert(
        @Query("amount") amount: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Header("apikey") apikey: String
    ): Response<ConvertJson>

}