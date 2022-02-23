package com.example.aibank.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoDataItem(
    @SerializedName("current_price") val current_price: Double?,
    @SerializedName("id") val id: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("market_cap_change_24h") val marketCap: String?,
    @SerializedName("low_24h") val low24h: Double?,
    @SerializedName("high_24h") val high24h: Double?,
    @SerializedName("name") val name: String?,
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("price_change_percentage_24h")val priceChanged24H: Double?,
    @SerializedName("ath")val ath: String,
    @SerializedName("atl")val atl: String
) : Parcelable