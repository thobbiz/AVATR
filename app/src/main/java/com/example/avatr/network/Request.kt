package com.example.avatr.network



data class PromptRequest(
    val inputs: String,
    val parameters: Parameters? = null
)

data class Parameters(
    val guidanceScale: Float? = null,
//    val negativePrompt: String? = null,
    val num_inference_steps: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val scheduler: String? = null,
    val seed: Int? = null
)
