package com.example.avatr.data

enum class AIModel(val displayName: String, val postUrl: String) {
    STABLE_DIFFUSION("Stable Diffusion",  "models/stabilityai/stable-diffusion-xl-base-1.0"),
    BLACK_FOREST_FLUX("Black Forest - Flux",  "models/black-forest-labs/FLUX.1-schnell")
}