package br.com.fernandosini.bookishadventure.services.implementations

import br.com.fernandosini.bookishadventure.models.ErrorResponse
import br.com.fernandosini.bookishadventure.services.LoginService

open class LoginServiceImpl : LoginService {

    override suspend fun login(email: String, password: String): Result<Any>? {
        val data = super.login(email, password)

        return when (data) {
            is String -> return Result.success<String>(data.toString())

            is ErrorResponse -> return Result.failure<ErrorResponse>(Exception(data.toString()))
            else -> return Result.failure(Exception("Unknown error"))
        }
    }

    override suspend fun logout(): Result<Any>? {
        val data = super.logout()
        return when (data) {
            is String -> return Result.success<String>(data.toString())

            is ErrorResponse -> return Result.failure<ErrorResponse>(Exception(data.toString()))
            else -> return Result.failure(Exception("Unknown error"))
        }
    }


}