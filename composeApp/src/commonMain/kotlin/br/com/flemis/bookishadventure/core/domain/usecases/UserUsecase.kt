package br.com.flemis.bookishadventure.core.domain.usecases

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.repository.UserRepository
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserUseCase : KoinComponent {
    private val userRepository: UserRepository by inject()
    suspend fun getUserDataFromLocal(userID: Long): Result<UserEntity> {
        val userData = userRepository.getUserFromLocal(userID)
        return when (userData) {
            is UserEntity -> Result.success(userData)
            else -> Result.failure(Exception("User not found"))
        }
    }

    suspend fun getUserDataFromApi(userId: Long): Result<User>? {
        val userData = userRepository?.getUserDataFromRemote(userId)
        return userData?.fold(
            onSuccess = { user -> Result.success(user) },
            onFailure = { error -> Result.failure(Exception("User not found")) },
        )

    }

}