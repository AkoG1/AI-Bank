package com.example.aibank.ui.network

import com.example.aibank.models.CryptoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    @GET("/api/v3/coins/markets")
    suspend fun getData(
        @Query("vs_currency")
        vs_currency:String,
        @Query("order")
        order : String,
        @Query("per_page")
        perPage: Int,
        @Query("sparkline")
        sparkline: Boolean
    ): Response<MutableList<CryptoData.CryptoDataItem>>

    @GET("/api/v3/coins/markets")
    suspend fun getDataById(
        @Query("vs_currency")
        vs_currency:String,
        @Query("ids")
        id: String,
        @Query("order")
        order : String,
        @Query("per_page")
        perPage: Int,
        @Query("sparkline")
        sparkline: Boolean
    ): Response<MutableList<CryptoData.CryptoDataItem>>

}