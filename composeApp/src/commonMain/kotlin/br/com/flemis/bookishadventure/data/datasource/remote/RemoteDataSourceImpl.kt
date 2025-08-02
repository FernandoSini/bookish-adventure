package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.remote.services.AuthService
import br.com.flemis.bookishadventure.data.datasource.remote.services.UserService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RemoteDataSourceImpl :
    RemoteDataSource, KoinComponent {
    private val userService: UserService by inject()
    private val authService: AuthService by inject()

    override suspend fun getUserDataFromApi(userID: Long): User? {
        TODO("Not yet implemented")
        userService.getUserData(userID)
    }

    override suspend fun login(body: HashMap<String,String>): Result<User> {
        return authService.login(body).fold(
            onSuccess = { user ->
                Result.success(user)
            },
            onFailure = { error ->

                Result.failure(error)
            }
        )
    }

    override suspend fun register(email: String, password: String) {
        TODO("Not yet implemented")
    }
}