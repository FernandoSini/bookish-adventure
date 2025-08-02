package br.com.flemis.bookishadventure.data.datasource.remote.services.implementations

import br.com.flemis.bookishadventure.data.datasource.remote.services.UserService

class UserServiceImpl : UserService {
    override suspend fun getUserData(userId: Long): Any? {
        return super.getUserData(userId)
    }
}