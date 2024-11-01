package com.example.lab1.network

import android.util.Log
import com.example.lab1.Character
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.json.Json
import java.util.logging.Logger
import kotlin.time.Duration.Companion.seconds

interface KtorNetworkApi {
    suspend fun getCharacters(): List<Character>
}
private const val NETWORK_BASE_URL = "www.anapioficeandfire.com/api"

class DataSource :KtorNetworkApi {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
    }

    private val client: HttpClient by lazy {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) { json(json) }

            install(HttpTimeout) {
                connectTimeoutMillis = 20.seconds.inWholeMilliseconds
                requestTimeoutMillis = 60.seconds.inWholeMilliseconds
                socketTimeoutMillis = 20.seconds.inWholeMilliseconds
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    override suspend fun getCharacters(): List<Character> {
        return try {
            client.get {
                url {
                    host = NETWORK_BASE_URL
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    path("characters")
                    parameters.append("pageSize", "50")
                }
            }.let { response ->
                Log.d("Ktor Response", response.body().toString())
                response.body()
            }
        } catch (exception: Exception) {
            Log.e("Error", exception.message.toString())
            emptyList()
        }
    }

    override suspend fun getCharacterById(id: Int): Character? {
        return try {
            client.get {
                url {
                    host = NETWORK_BASE_URL
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    path("characters", "$id")
                }
            }.let { response ->
                Log.d("Ktor Response", response.body().toString())
                response.body()
            }
        } catch (exception: Exception) {
            Log.e("Error", exception.message.toString())
            null
        }
    }
}