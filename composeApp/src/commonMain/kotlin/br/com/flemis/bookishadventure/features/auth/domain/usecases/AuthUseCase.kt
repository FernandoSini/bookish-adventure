package br.com.flemis.bookishadventure.features.auth.domain.usecases

import br.com.flemis.bookishadventure.features.auth.domain.repository.AuthRepository
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthUseCase : KoinComponent {
    private val authRepository: AuthRepository by inject()
    suspend fun login(body:HashMap<String,String>, state: MutableStateFlow<AuthState>): StateFlow<AuthState> {
       return authRepository.login(body,state)
       /* return when (data) {
            is User -> {
                //model
                // authRepository.saveUser(data)
                Result.success(data)
            }
            else -> Result.failure(Exception("Login failed"))
        }*/
    }

    suspend fun logout(): Unit {
        authRepository.logout()
    }

    suspend fun register(username: String, password: String): Result<Unit> {
        authRepository.register(username, password);

        return Result.success(Unit);
    }
}