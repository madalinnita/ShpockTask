package com.example.shpocktask.data.models

import com.google.gson.annotations.SerializedName

data class PirateShipsResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("ships") val ships: List<PirateShip>
)

