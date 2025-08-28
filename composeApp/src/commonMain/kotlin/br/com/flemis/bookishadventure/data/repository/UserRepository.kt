package br.com.flemis.bookishadventure.data.repository

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel

interface UserRepository {
    suspend fun getUserFromLocal(userID: Long): UserModel?
    suspend fun getUserDataFromRemote(userID: Long): Result<User>
    suspend fun deleteUserFromLocal(userID: Long): Result<Unit>
}