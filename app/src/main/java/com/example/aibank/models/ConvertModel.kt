package com.example.aibank.models


import com.google.gson.annotations.SerializedName

data class ConvertModel(
    @SerializedName("amount")
    val amount: String?,
    @SerializedName("from")
    val from: String?,
    @SerializedName("to")
    val to: String?,
    @SerializedName("value")
    val value: String?
)