//package com.example.aibank.ui.network
//
//
//import com.example.aibankv10.ui.network.ApiService
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkClientForCurrencies {
//
//    const val BASE_URL = "https://api.tbcbank.ge/v1/"
//
//    @Singleton
//    @Provides
//    fun provideGson() : GsonConverterFactory {
//        return GsonConverterFactory.create()
//    }
//
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit.Builder {
//        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(gsonConverterFactory)
//    }
//
//    @Singleton
//    @Provides
//    fun provideApiService(retrofit: Retrofit.Builder) : ApiService {
//        return retrofit.build().create(ApiService::class.java)
//    }
//
//}