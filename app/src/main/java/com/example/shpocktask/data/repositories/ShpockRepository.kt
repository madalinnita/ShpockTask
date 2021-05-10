package com.example.shpocktask.data.repositories

import com.example.shpocktask.data.api.ApiHelper

class ShpockRepository(private val apiHelper: ApiHelper) {
    suspend fun getPirateShips() = apiHelper.getPirateShips()
}