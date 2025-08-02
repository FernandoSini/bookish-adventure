package br.com.flemis.bookishadventure.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.core.domain.usecases.UserUseCase
import br.com.flemis.bookishadventure.core.presentation.ui.viewmodel.states.DynamicState
import br.com.flemis.bookishadventure.core.errors.states.ErrorResponseState
import br.com.flemis.bookishadventure.presentation.viewmodel.states.UserState
import br.com.flemis.bookishadventure.utils.CustomResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

//KoinComponent é usado somente no viewModel e no useCase, pois o Koin é usado para injeção de dependência
class UserViewModel() : ViewModel(), KoinComponent {
    private val userUseCase: UserUseCase by inject()
    val _state = MutableStateFlow(
        DynamicState(
            currentState = UserState(), errorResponseState = ErrorResponseState()
        )
    )
    val state = _state.onStart {

    }.stateIn(
        scope = viewModelScope,
        initialValue = DynamicState(
            currentState = UserState(), errorResponseState = ErrorResponseState(),
        ),
        started = SharingStarted.WhileSubscribed(5_000L),
    );


    fun getUserData(userId: Long) {
        viewModelScope.launch {

            val data = userUseCase.getUserDataFromApi(userId)?.fold(onSuccess = { user ->
                _state.update { currentDynamicState ->
                    currentDynamicState.copy(
                        currentState = UserState(user)
                    )
                }
                // Handle success, e.g., update UI state with user data
            }, onFailure = { exception ->
                exception.message
                _state.update { currentDynamicState ->
                    currentDynamicState.copy(
                        errorResponseState = ErrorResponseState(error = exception.message)
                    )
                }
                // Handle error, e.g., show error message
            })
        }
    }

    fun getUserDataFromLocal(userId: Long) {
        viewModelScope.launch {
            userUseCase.getUserDataFromLocal(userId).fold(
                onSuccess = { user ->
                    _state.update { currentDynamicState ->
                        currentDynamicState.copy(
                            currentState = UserState(user as User)
                        )
                    }
                    // user as User
                    CustomResult.Success(user)

                    // Handle success, e.g., update UI state with user data
                }, onFailure = { exception ->

                    _state.update { currentDynamicState ->
                        currentDynamicState.copy(
                            errorResponseState = ErrorResponseState(error = exception.message)
                        )
                    }
                    //exception.message
                    CustomResult.Error(exception.message.toString())
                    // Handle error, e.g., show error message
                })
        }

    }


    /*
      //só pegando como exemplo

      val user = userUsecase.getUser()
        val isLoggedIn = userUsecase.isLoggedIn()
        val isDarkMode = userUsecase.isDarkMode()

        fun setUser(user: String) {
            userUsecase.setUser(user)
        }

        fun setIsLoggedIn(isLoggedIn: Boolean) {
            userUsecase.setIsLoggedIn(isLoggedIn)
        }

        fun setIsDarkMode(isDarkMode: Boolean) {
            userUsecase.setIsDarkMode(isDarkMode)
        }*/
}