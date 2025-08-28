package br.com.flemis.bookishadventure.features.auth.domain.repository

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository  {
    suspend fun login(body: HashMap<String,String>, state: MutableStateFlow<AuthState>): StateFlow<AuthState>
    suspend fun logout()
    suspend fun register(email: String, password: String)
    suspend fun fetchLocalData(): User?
}