package br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.flemis.bookishadventure.core.domain.usecases.AuthUseCase
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state.AuthState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {
    private val authUseCase: AuthUseCase by inject()
    private val _state = MutableStateFlow(AuthState())

    var state = _state.onStart { }.stateIn(
        viewModelScope, initialValue =
            AuthState(),
        started = SharingStarted.Companion.Lazily
    )

    fun login(body: HashMap<String,String>, snackbarHostState: SnackbarHostState) {
        viewModelScope.launch {
            state = authUseCase.login(body, _state)
            if (state.value.errorResponseState != null) {
                var result = snackbarHostState.showSnackbar(
                    duration = SnackbarDuration.Short,
                    message = state.value.errorResponseState!!.error!!,
                    withDismissAction = false,
                    actionLabel = "Close"
                )

            }

            //println("AuthViewModel: ${state.value.currentState.userState?.user?.username}")
        }
    }

    fun register(username: String, password: String) {}


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}