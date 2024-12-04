package com.example.avatr.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.gson.gson
import retrofit2.http.POST

private const val BASE_URL = "https://api.stability.ai"

interface StableDiffusionApiService {
    @POST("/v2beta/stable-image/generate/sd3")
        suspend fun generateImage(request: TextToImageRequest): StableDiffusionResponse
}

class StableDiffusionApiImpl() : StableDiffusionApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    override suspend fun generateImage(request: TextToImageRequest): StableDiffusionResponse {
        return client.post("$BASE_URL//v2beta/stable-image/generate/sd3") {
            setBody(request)
        }.body()
    }
}