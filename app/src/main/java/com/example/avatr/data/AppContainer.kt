package com.example.avatr.data

import android.content.Context
import com.example.avatr.network.ModelsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val modelsRepository: ModelsRepository
    val imageRepository: ImageRepository
    val savedPhotoRepository: SavedPhotosRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {

    private val baseUrl = "https://api-inference.huggingface.co/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase connect timeout
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS) // Increase read timeout
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private val retrofitService: ModelsApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ModelsApi::class.java)
    }

    override val modelsRepository: ModelsRepository by lazy {
        NetworkModelsRepository(retrofitService)
    }

    override val imageRepository: ImageRepository by lazy {
        ImageRepository(context = context)
    }

    override val savedPhotoRepository: SavedPhotosRepository by lazy {
        OfflineSavedPhotoRepository(SavedPhotosDatabase.getDatabase(context).savedPhotoDao())
    }
}