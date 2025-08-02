package br.com.flemis.bookishadventure.core.presentation.ui.viewmodel.states

import br.com.flemis.bookishadventure.core.errors.states.ErrorResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class DynamicState<T>(
    val currentState: T,
    var errorResponseState: ErrorResponseState? = ErrorResponseState(),
) {
    fun getState(): T? {
        return currentState
    }
}

fun <T> MutableStateFlow<DynamicState<T>>.setData(data: T) {
    update { it.copy(currentState = data, errorResponseState = null) }
}

fun <T> MutableStateFlow<DynamicState<T>>.setError(error: ErrorResponseState) {
    update { it.copy(errorResponseState = error) }
}

