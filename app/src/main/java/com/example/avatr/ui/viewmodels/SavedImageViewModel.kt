package com.example.avatr.ui.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avatr.data.ImageRepository
import com.example.avatr.data.SavedPhoto
import com.example.avatr.data.SavedPhotosRepository
import com.example.avatr.ui.components.loadImageFromStorage
import com.example.avatr.ui.screens.SavedImageDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedImageViewModel(
    savedStateHandle: SavedStateHandle,
    private val savedPhotosRepository: SavedPhotosRepository,
    private val imageRepository: ImageRepository,
): ViewModel() {
    private val savedPhotoId: Int = checkNotNull(savedStateHandle[SavedImageDestination.savedPhotoIdArg])

    val uiState: StateFlow<SavedPhotoUiState> = savedPhotosRepository.getSavedPhoto(savedPhotoId)
        .filterNotNull()
        .map{
            SavedPhotoUiState(savedPhotoDetails = it.toSavedPhotoDetails())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SavedPhotoUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    suspend fun deleteSavedPhoto() {
        savedPhotosRepository.deleteSavedPhoto(uiState.value.savedPhotoDetails.toSavedPhoto())
    }

    fun saveImageToGallery(filePath: String) {
        val bitmap = loadImageFromStorage(filePath)
        CoroutineScope(Dispatchers.IO).launch {
            viewModelScope.launch {
                if (bitmap != null) {
                    imageRepository.SaveImageToGallery(bitmap)
                }
            }
        }
    }

}

data class SavedPhotoUiState(
    val savedPhotoDetails: SavedPhotoDetails = SavedPhotoDetails()
)

data class SavedPhotoDetails(
    val id: Int = 0,
    val prompt: String = "",
    val base64FilePath: String = "",
    val date: String = ""
)


private fun SavedPhoto.toSavedPhotoDetails() = SavedPhotoDetails(
    id = id,
    prompt = prompt,
    base64FilePath = base64FilePath,
    date = date
)

fun SavedPhotoDetails.toSavedPhoto(): SavedPhoto = SavedPhoto(
    id = id,
    prompt = prompt,
    base64FilePath = base64FilePath,
    date = date
)