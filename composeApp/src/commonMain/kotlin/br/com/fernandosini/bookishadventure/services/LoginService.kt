package br.com.fernandosini.bookishadventure.services

import br.com.fernandosini.bookishadventure.services.implementations.LoginServiceImpl
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException


interface LoginService : RequestService {

    open suspend fun login(email: String, password: String): Any? {
        try {
            val response =
                post("https://example.com/login", mapOf("email" to email, "password" to password))
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

    open suspend fun logout(): Any? {
        try {
            val response = get("https://example.com/logout")
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

    companion object {
        val instance: LoginService by lazy { LoginServiceImpl() }
    }
}