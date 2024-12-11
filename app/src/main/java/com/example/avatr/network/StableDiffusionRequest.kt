package com.example.avatr.network

import com.google.gson.annotations.SerializedName

data class TextToImageRequest(
    val prompt: String,
    val negativePrompt: String? = null
)

data class ImageToImageRequest(
    val prompt: String,
    val image: String,
    val strength: Int,
    val mode: String,
    val negativePrompt: String? = null
)

data class StableDiffusionResponse(
    val image: String,
    @SerializedName("finish_reason") val finishReason: String,
    val seed: Long
)