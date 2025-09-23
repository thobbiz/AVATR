package com.example.avatr.data

import com.example.avatr.network.ModelsApi
import com.example.avatr.network.Parameters
import com.example.avatr.network.PromptRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface ModelsRepository {
    suspend fun GenerateImageFromText(url: String, prompt: String, negativePrompt: String?): ResponseBody
    suspend fun sdGenerateImageFromImage(prompt: String, negativePrompt: String?, image: MultipartBody.Part): Response<ResponseBody>
}

class NetworkModelsRepository(private val modelsApi: ModelsApi): ModelsRepository {

    override suspend fun sdGenerateImageFromImage(prompt: String, negativePrompt: String?, image: MultipartBody.Part): Response<ResponseBody> = modelsApi.sdGenerateImageFromImage(prompt = prompt.toRequestBody(), negativePrompt = negativePrompt?.toRequestBody(), image = image)
    override suspend fun GenerateImageFromText(url: String, prompt: String, negativePrompt: String?): ResponseBody = modelsApi.GenerateImageFromText(
        url = url,
        body = PromptRequest(
            inputs = prompt,
            parameters = Parameters(
//                negativePrompt = negativePrompt
            )
        )
    )
}