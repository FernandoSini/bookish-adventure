package br.com.flemis.bookishadventure.data.datasource.remote.services.implementations

import br.com.flemis.bookishadventure.core.domain.models.ErrorResponse
import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.remote.services.AuthService


class AuthServiceImpl : AuthService {

    override suspend fun login(body: HashMap<String,String>): Result<User> {
        val data = super.login(body)

        return data.fold(
            onSuccess = { user ->
                Result.success(user)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun logout(): Result<Any>? {
        val data = super.logout()
        return when (data) {
            is String -> return Result.success<String>(data.toString())

            is ErrorResponse -> return Result.failure<ErrorResponse>(Exception(data.toString()))
            else -> return Result.failure(Exception("Unknown error"))
        }
    }

    override suspend fun register(email: String, password: String): Result<Any>? {
        val data = super.register(email, password)
        return when (data) {
            is String -> return Result.success<String>(data.toString())

            is ErrorResponse -> return Result.failure<ErrorResponse>(Exception(data.toString()))
            else -> return Result.failure(Exception("Unknown error"))
        }
    }

    override suspend fun forgotPassword(email: String): Any? {
        return super.forgotPassword(email)
    }

    override suspend fun verifyCode(email: String, code: String): Any? {
        return super.verifyCode(email, code)

    }

}
