package com.example.avatr.data

import com.example.avatr.network.StableDiffusionApi
import com.example.avatr.network.StableDiffusionResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

interface StableDiffusionRepository {
    suspend fun generateImageFromText(prompt: String, negativePrompt: String?): StableDiffusionResponse
    suspend fun generateImageFromImage(prompt: String, negativePrompt: String?, image: MultipartBody.Part): StableDiffusionResponse
}

class NetworkStableDiffusionRepository(private val stableDiffusionApi: StableDiffusionApi): StableDiffusionRepository {
    override suspend fun generateImageFromText(prompt: String, negativePrompt: String?): StableDiffusionResponse = stableDiffusionApi.generateImageFromText(prompt = prompt.toRequestBody(), negativePrompt = negativePrompt?.toRequestBody())
    override suspend fun generateImageFromImage(prompt: String, negativePrompt: String?, image: MultipartBody.Part): StableDiffusionResponse = stableDiffusionApi.generateImageFromImage(prompt = prompt.toRequestBody(), negativePrompt = negativePrompt?.toRequestBody(), image = image)
}