package com.example.shpocktask.data.api

import com.example.shpocktask.data.models.PirateShipsResponse

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPirateShips(): PirateShipsResponse = apiService.getPirateShips()
}