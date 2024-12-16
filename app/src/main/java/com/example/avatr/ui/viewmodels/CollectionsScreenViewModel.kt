package com.example.avatr.ui.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avatr.data.SavedPhoto
import com.example.avatr.data.SavedPhotosRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CollectionsScreenViewModel (savedPhotosRepository: SavedPhotosRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val collectionsUiState: StateFlow<CollectionsUiState> = savedPhotosRepository.getAllSavedPhoto().map { CollectionsUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = CollectionsUiState()
        )
}

data class CollectionsUiState(val savedPhotosList: List<SavedPhoto> = listOf())