package br.com.flemis.bookishadventure.features.auth.data.repository

import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.local.LocalDataSource
import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel
import br.com.flemis.bookishadventure.data.repository.UserRepository
import br.com.flemis.bookishadventure.data.datasource.remote.RemoteDataSource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UserRepositoryImpl : UserRepository, KoinComponent {
    private val localDataSource: LocalDataSource by inject()
    private val remoteDataSource: RemoteDataSource by inject()
    override suspend fun getUserFromLocal(userID: Long): UserModel? {
        val data = localDataSource.getUserFromLocal(userID)
        return data ?: null
    }



    override suspend fun getUserDataFromRemote(userID: Long): Result<User> {
        val userData = remoteDataSource?.getUserDataFromApi(userID)

        return when (userData) {
            is User -> Result.success(userData)
            else -> Result.failure(Exception("User not found"))
        }
    }

    override suspend fun deleteUserFromLocal(userID: Long): Result<Unit> {
        TODO("Not yet implemented")
    }
}