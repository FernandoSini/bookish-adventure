package br.com.flemis.bookishadventure.data.datasource.remote.services
//a pasta remote pode ser chamada de network
import br.com.flemis.bookishadventure.config.KtorClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

interface RequestService {

    suspend fun get(url: String): HttpResponse {
        val response = KtorClient.httpClient.get(url) {
            headers {
                append("Accept", "application/json")
                //append("x-custom-header", "value")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun post(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.post(urlString) {
           /* url {
                urlString
            }*/
            if (!body.isNullOrEmpty()) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header", "value")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun put(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.put {
            url {
                urlString
            }
            if (!body.isNullOrEmpty()) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header", "value")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun delete(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = KtorClient.httpClient.delete {
            url {
                urlString
            }
            if (!body.isNullOrEmpty()) {
                setBody(Json.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
                //append("x-custom-header", "value")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }
}