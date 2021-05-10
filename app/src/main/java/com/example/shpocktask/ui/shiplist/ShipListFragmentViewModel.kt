package com.example.shpocktask.ui.shiplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shpocktask.data.models.PirateShipsResponse
import com.example.shpocktask.data.repositories.ShpockRepository
import com.example.shpocktask.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShipListFragmentViewModel(private val shpockRepository: ShpockRepository) : ViewModel() {

    private val pirateShipsResponse = MutableLiveData<Resource<PirateShipsResponse>>()

    fun getPirateShips() {
        viewModelScope.launch {
            pirateShipsResponse.postValue(Resource.loading(data = null))
            withContext(Dispatchers.IO) {
                try {
                    pirateShipsResponse.postValue(
                        Resource.success(
                            data = shpockRepository.getPirateShips()
                        )
                    )
                } catch (exception: Exception) {
                    pirateShipsResponse.postValue(
                        Resource.error(
                            data = null,
                            message = exception.message ?: "Error Occurred!"
                        )
                    )
                }
            }
        }
    }

    fun getPirateShipsResponse(): LiveData<Resource<PirateShipsResponse>> = pirateShipsResponse
}