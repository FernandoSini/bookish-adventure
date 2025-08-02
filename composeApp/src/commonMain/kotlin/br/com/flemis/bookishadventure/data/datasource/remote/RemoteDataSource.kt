package br.com.flemis.bookishadventure.data.datasource.remote

import br.com.flemis.bookishadventure.core.domain.models.User

interface RemoteDataSource {
    suspend fun getUserDataFromApi(userID: Long): User?
    suspend fun login(body: HashMap<String,String>): Result<User>
    suspend fun register(email:String,password:String)
}