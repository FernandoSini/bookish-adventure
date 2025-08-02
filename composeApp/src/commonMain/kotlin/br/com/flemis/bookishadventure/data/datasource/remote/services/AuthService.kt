package br.com.flemis.bookishadventure.data.datasource.remote.services

import br.com.flemis.bookishadventure.core.domain.models.ErrorResponse
import br.com.flemis.bookishadventure.core.domain.models.User
import br.com.flemis.bookishadventure.data.datasource.remote.services.implementations.AuthServiceImpl
import br.com.flemis.bookishadventure.core.presentation.navigation.routes.HttpRoutes
import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.serialization.json.Json

interface AuthService : RequestService {

    open suspend fun login(body: HashMap<String, String>): Result<User> {
        try {
            val response =
                post(HttpRoutes.DEFAULT_URL + "/auth/login", body)
            if (response.status.value >= 200 && response.status.value <= 299) {
                Napier.d { "AuthService: Login successful" }
                return Result.success(Json.decodeFromString<User>(response.body()))
            } else {
                val data = Json.decodeFromString<ErrorResponse>(response.body())
                return Result.failure(Exception("Error: ${data.error.message}"))
            }
        } catch (e: RedirectResponseException) {
            return Result.failure(Exception(e.message))
        } catch (e: ClientRequestException) {
            return Result.failure(Exception(e.message))
        } catch (e: ServerResponseException) {
            return Result.failure(Exception(e.message))
        } catch (e: Exception) {

            Napier.e { "AuthService: Error during login: ${e.message}" }
            return Result.failure(Exception("Server error. Please try again later."))
        }
    }

    open suspend fun logout(): Any? {
        try {
            val response = get(HttpRoutes.DEFAULT_URL + "/auth/logout")
            return response

        } catch (e: RedirectResponseException) {
            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {
            return e.response
        } catch (e: Exception) {
            return e
        }
    }

    open suspend fun register(email: String, password: String): Any? {
        try {
            val response =
                post(HttpRoutes.DEFAULT_URL+"/register", mapOf("email" to email, "password" to password))
            return response
        } catch (e: RedirectResponseException) {
            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {
            return e.response
        } catch (e: Exception) {
            return e
        }
    }

    open suspend fun forgotPassword(email: String): Any? {
        try {
            val response =
                post(HttpRoutes.DEFAULT_URL+"/forgot-password", mapOf("email" to email))
            return response
        } catch (e: RedirectResponseException) {
            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {
            return e.response
        } catch (e: Exception) {
            return e
        }
    }

    open suspend fun verifyCode(email: String, code: String): Any? {
        try {
            val response =
                post(HttpRoutes.DEFAULT_URL + "/verify-code", mapOf("email" to email, "code" to code))
            return response
        } catch (e: RedirectResponseException) {
            return e.response
        } catch (e: ClientRequestException) {
            return e.response
        } catch (e: ServerResponseException) {
            return e.response
        } catch (e: Exception) {
            return e
        }
    }

    companion object Companion {
        val instance: AuthService by lazy { AuthServiceImpl() }
    }
}