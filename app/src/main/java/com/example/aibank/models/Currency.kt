package com.example.aibank.models


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("base")
    val base: String?,
    @SerializedName("commercialRatesList")
    val commercialRatesList: List<CommercialRates>?
) {
    data class CommercialRates(
        @SerializedName("buy")
        val buy: Double?,
        @SerializedName("currency")
        val currency: String?,
        @SerializedName("sell")
        val sell: Double?
    )
}