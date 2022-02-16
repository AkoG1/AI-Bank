package com.example.aibank.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Currency(
    @SerializedName("base")
    val base: String?,
    @SerializedName("commercialRatesList")
    val commercialRatesList: List<CommercialRates>?
) : Parcelable {
    @Parcelize
    data class CommercialRates(
        @SerializedName("buy")
        val buy: Double?,
        @SerializedName("currency")
        val currency: String?,
        @SerializedName("sell")
        val sell: Double?
    ) : Parcelable
}