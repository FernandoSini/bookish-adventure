package br.com.flemis.bookishadventure.data.repository.implementations

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.local.LocalDataSource
import br.com.flemis.bookishadventure.data.repository.AuthRepository
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserEntity
import br.com.flemis.bookishadventure.data.datasource.remote.RemoteDataSource
import br.com.flemis.bookishadventure.features.auth.presentation.ui.viewmodel.state.AuthState
import br.com.flemis.bookishadventure.core.errors.states.ErrorResponseState
import br.com.flemis.bookishadventure.presentation.viewmodel.states.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthRepositoryImpl : AuthRepository, KoinComponent {
    private val authRemoteDatasource: RemoteDataSource by inject()
    private val authLocalDataSource: LocalDataSource by inject()
    override suspend fun login(
        body: HashMap<String, String>,
        state: MutableStateFlow<AuthState>
    ): StateFlow<AuthState> {

        state.update { authState ->
            authState.copy(isLoading = true)
        }
        val result = authRemoteDatasource.login(body)
        result.fold(
            onSuccess = {
                authLocalDataSource.saveUser(
                    UserEntity(
                        id = it.id,
                        firstname = it.firstname,
                        lastname = it.lastname,
                        username = it.username,
                        email = it.email,
                        password = it.password,
                        avatar = it.avatar,
                        isVerified = it.isVerified
                    )
                )
                state.update { authState ->
                    authState.copy(
                        isLoading = false,
                        userState = UserState(user = it),
                        errorResponseState = null,
                        isVerified = it.isVerified
                    )
                }
            },
            onFailure = {
                state.update { authState ->
                    authState.copy(
                        isLoading = false,
                        userState = null,
                        errorResponseState = ErrorResponseState(error = it.message),
                        isVerified = false
                    )
                }

            }
        )
        return state
    }

    override suspend fun logout() {
        TODO("Not yet implemented")

    }

    override suspend fun register(email: String, password: String) {

        authRemoteDatasource.register(email, password)
    }

    override suspend fun fetchLocalData(): User? {
        val userEntity = authLocalDataSource.getCurrentUser()

        return userEntity?.let {
            User(
                id = it.id,
                firstname = it.firstname,
                lastname = it.lastname,
                username = it.username,
                email = it.email,
                password = it.password,
                avatar = it.avatar,
                isVerified = it.isVerified
            )
        }
    }
}

