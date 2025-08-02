package br.com.flemis.bookishadventure.data.datasource.local

import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserEntity

interface LocalDataSource  {
    suspend fun getUserFromLocal(userID: Long): UserEntity?
    suspend fun saveUser(user: UserEntity): Unit
    suspend fun getCurrentUser():UserEntity
}