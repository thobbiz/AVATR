package com.example.avatr.data

import android.content.Context
import com.example.avatr.network.StableDiffusionApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val stableDiffusionRepository: StableDiffusionRepository
    val imageRepository: ImageRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {

    private val BASE_URL = "https://api.stability.ai/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase connect timeout
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase read timeout
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private val retrofitService: StableDiffusionApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StableDiffusionApi::class.java)
    }

    override val stableDiffusionRepository: StableDiffusionRepository by lazy {
        NetworkStableDiffusionRepository(retrofitService)
    }

    override val imageRepository: ImageRepository by lazy {
        ImageRepository(context = context)
    }
}