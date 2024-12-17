package com.example.avatr.data

import kotlinx.coroutines.flow.Flow

class OfflineSavedPhotoRepository(private val savedPhotoDao: SavedPhotoDao): SavedPhotosRepository {
    override fun getAllSavedPhoto(): Flow<List<SavedPhoto>> = savedPhotoDao.getAllSavedPhotos()

    override fun getSavedPhoto(id: Int): Flow<SavedPhoto?> = savedPhotoDao.getSavedPhoto(id)

    override suspend fun deleteSavedPhoto(savedPhoto: SavedPhoto) = savedPhotoDao.delete(savedPhoto)

    override suspend fun insertSavedPhoto(savedPhoto: SavedPhoto) = savedPhotoDao.insert(savedPhoto)

    override fun searchByPrompt(prompt: String): Flow<SavedPhoto?> = savedPhotoDao.searchByPrompt(prompt)
}