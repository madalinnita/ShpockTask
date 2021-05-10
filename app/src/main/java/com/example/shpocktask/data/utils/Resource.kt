package com.example.shpocktask.data.utils

data class Resource<out T>(val status: CallStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> = Resource(status = CallStatus.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = CallStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> = Resource(status = CallStatus.LOADING, data = data, message = null)
    }
}