package com.example.avatr.network

import com.google.gson.annotations.SerializedName

data class StableDiffusionResponse(
    val image: String,
    @SerializedName("finish_reason") val finishReason: String,
    val seed: Long
)