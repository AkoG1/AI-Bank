package com.example.aibank.models

import com.google.gson.annotations.SerializedName

class CryptoData : ArrayList<CryptoData.CryptoDataItem>() {
    data class CryptoDataItem(
        @SerializedName("current_price") val current_price: Double?,
        @SerializedName("id") val id: String?,
        @SerializedName("image") val image: String?,
        @SerializedName("last_updated") val last_updated: String?,
        @SerializedName("low_24h") val low_24h: Double?,
        @SerializedName("name") val name: String?,
        @SerializedName("price_change_24h") val price_change_24h: Double?,
        @SerializedName("price_change_percentage_24h") val price_change_percentage_24h: Double?,
        @SerializedName("symbol") val symbol: String?
    )
}