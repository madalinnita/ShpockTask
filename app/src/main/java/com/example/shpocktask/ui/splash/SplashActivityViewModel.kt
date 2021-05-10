package com.example.shpocktask.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivityViewModel(application: Application): AndroidViewModel(application) {

    private val delayTime: Long = 3000
    private val splashState = MutableLiveData<SplashState>()

    fun initSplashScreen() {
        viewModelScope.launch {
            splashState.value = SplashState.STARTED
            delay(delayTime)
            splashState.value = SplashState.FINISHED
        }
    }

    fun getSplashState(): LiveData<SplashState> = splashState

}