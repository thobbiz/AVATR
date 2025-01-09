package com.example.avatr.network

import com.example.avatr.BuildConfig
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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