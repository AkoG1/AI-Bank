package com.example.aibank.ui.network


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkClient {

    private const val BASE_URL_TBC = "https://api.tbcbank.ge/v1/"

    private const val BASE_URL_CRYPTO = "https://api.coingecko.com"

    @Singleton
    @Provides
    fun provideGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }


    @Singleton
    @Provides
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit.Builder {
        return Retrofit.Builder().addConverterFactory(gsonConverterFactory)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit.baseUrl(BASE_URL_TBC).build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCryptoApiService(retrofit: Retrofit.Builder): CryptoApiService {
        return retrofit.baseUrl(BASE_URL_CRYPTO).build().create(CryptoApiService::class.java)
    }

}