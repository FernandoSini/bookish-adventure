package br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state

import br.com.flemis.bookishadventure.core.errors.states.ErrorResponseState
import br.com.flemis.bookishadventure.presentation.viewmodel.states.UserState

data class AuthState(
    var isLoading: Boolean = false,
    var userState: UserState? = null,
    var errorResponseState: ErrorResponseState? = ErrorResponseState(),
    var isVerified: Boolean = false,
)