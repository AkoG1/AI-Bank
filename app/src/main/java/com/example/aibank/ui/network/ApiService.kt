package com.example.aibankv10.ui.network


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path


interface ApiService {

    @GET("{number}/{sender}/{message}")
    suspend fun requestSMS(@Path("number")phoneNumber: String,
                           @Path("sender")sender: String,
                           @Path("message")message: String,
                           @Header("x-rapidapi-host")xrapidapihost: String,
                           @Header("x-rapidapi-key")apikey: String)
}