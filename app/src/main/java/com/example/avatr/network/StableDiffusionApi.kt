package com.example.avatr.network

import com.example.avatr.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StableDiffusionApi {
    @Multipart
    @POST("/v2beta/stable-image/generate/sd3")
    suspend fun generateImageFromText(
        @Header("Authorization") apiKey: String = BuildConfig.API_KEY,
        @Header("Accept") accept: String = "application/json",
        @Part("prompt") prompt: RequestBody,
        @Part("aspect_ratio") aspectRatio: RequestBody = "1:1".toRequestBody(),
        @Part("model") model: RequestBody = "sd3.5-medium".toRequestBody(),
        @Part("negative_prompt") negativePrompt: RequestBody?
    ): StableDiffusionResponse

    @Multipart
    @POST("/v2beta/stable-image/generate/sd3")
    suspend fun generateImageFromImage(
        @Header("Authorization") apiKey: String = BuildConfig.API_KEY,
        @Header("Accept") accept: String = "application/json",
        @Part("prompt") prompt: RequestBody,
        @Part("negative_prompt") negativePrompt: RequestBody?,
        @Part("image") image: RequestBody,
        @Part("aspect_ratio") aspectRatio: RequestBody = "1:1".toRequestBody(),
        @Part("model") model: RequestBody = "sd3.5-medium".toRequestBody(),
        @Part("mode") mode: RequestBody? = "image-to-image".toRequestBody(),
        @Part("strength") strength: RequestBody? = "0".toRequestBody()
    ): StableDiffusionResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://api.stability.ai/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase connect timeout
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase read timeout
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()


    val instance: StableDiffusionApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StableDiffusionApi::class.java)
    }
}