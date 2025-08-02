package br.com.flemis.bookishadventure.utils

sealed class CustomResult<out T> {
    data class Success<out T>(val data: T) : CustomResult<T>()
    data class Error(val message: String) : CustomResult<Nothing>()
    object Loading : CustomResult<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }

    fun isError(): Boolean {
        return this is Error
    }

    fun isLoading(): Boolean {
        return this is Loading
    }
}