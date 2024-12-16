package com.example.avatr.data

import kotlinx.coroutines.flow.Flow

interface SavedPhotosRepository {
    suspend fun insertSavedPhoto(savedPhoto: SavedPhoto)

    fun getSavedPhoto(id: Int): Flow<SavedPhoto?>

    fun getAllSavedPhoto(): Flow<List<SavedPhoto>>

    suspend fun deleteSavedPhoto(savedPhoto: SavedPhoto)
}