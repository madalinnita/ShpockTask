package com.example.shpocktask.data.api

import com.example.shpocktask.data.models.PirateShipsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("pirateships")
    suspend fun getPirateShips(): PirateShipsResponse
}