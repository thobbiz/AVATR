package com.example.avatr.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.avatr.data.SavedPhotosRepository

class DeleteAllSavedArtViewModel (
    private val savedPhotosRepository: SavedPhotosRepository,
): ViewModel() {
    suspend fun DeleteAllSavedArt() {
        savedPhotosRepository.deleteAllSavedPhoto()
    }
}