package com.example.avatr.network

import com.example.avatr.BuildConfig
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelsApi {

    @Multipart
    @POST("/v2beta/stable-image/generate/sd3")
    suspend fun sdGenerateImageFromImage(
        @Header("Authorization") apiKey: String = BuildConfig.SD_KEY,
        @Header("Accept") accept: String = "image/jpeg",
        @Part("prompt") prompt: RequestBody,
        @Part("negative_prompt") negativePrompt: RequestBody?,
        @Part image: MultipartBody.Part,
        @Part("model") model: RequestBody = "sd3.5-medium".toRequestBody(),
        @Part("mode") mode: RequestBody? = "image-to-image".toRequestBody(),
        @Part("strength") strength: RequestBody = "0.7".toRequestBody()
    ): Response<ResponseBody>

    @POST
    suspend fun GenerateImageFromText(
        @retrofit2.http.Url url: String,
        @Header("Authorization") apiKey: String = "Bearer ${BuildConfig.HF_TOKEN}",
        @Body body: PromptRequest,
    ): ResponseBody
}