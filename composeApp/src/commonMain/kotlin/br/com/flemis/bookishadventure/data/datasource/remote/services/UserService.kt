package br.com.flemis.bookishadventure.data.datasource.remote.services

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

interface UserService : RequestService {

    suspend fun getUserData(userId: Long): Any? {
        try {
            val response =
                get("https://example.com/login")
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
}