package br.com.flemis.bookishadventure.data.datasource.local

import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel

interface LocalDataSource  {
    suspend fun getUserFromLocal(userID: Long): UserModel?
    suspend fun saveUser(user: UserModel): Unit
    suspend fun getCurrentUser():UserModel
}