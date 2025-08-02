package br.com.flemis.bookishadventure.data.datasource.remote

//import br.com.flemis.bookishadventure.config.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class NativeRequestService {

    suspend fun get(url: String): HttpResponse {
        val response = HttpClient().get(url) {
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun post(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = HttpClient().post {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun put(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = HttpClient().put {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun delete(urlString: String, body: Map<String, String>?): HttpResponse {
        val response = HttpClient().delete {
            url(urlString)
            if (!body.isNullOrEmpty()) {
                setBody(Json.Default.encodeToString(body))
            }
            headers {
                append("Accept", "application/json")
            }
            contentType(ContentType.Application.Json)
        }
        return response
    }
}