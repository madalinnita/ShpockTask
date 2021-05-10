package com.example.shpocktask.ui.shiplist.adapter

import com.example.shpocktask.data.models.PirateShip

interface ItemClickedCallback {
    fun selectedShip(ship: PirateShip)
}