package com.example.shpocktask.ui.shiplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.shpocktask.data.api.ApiHelper
import com.example.shpocktask.data.repositories.ShpockRepository

class ShipListFragmentViewModelFactory(private val apiHelper: ApiHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShipListFragmentViewModel::class.java)) {
            return ShipListFragmentViewModel(ShpockRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}