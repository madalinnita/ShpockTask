package com.example.shpocktask.data.models

import com.google.gson.annotations.SerializedName

data class PirateShip(
    @SerializedName("id") val id: Double,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("image") val image: String,
)
