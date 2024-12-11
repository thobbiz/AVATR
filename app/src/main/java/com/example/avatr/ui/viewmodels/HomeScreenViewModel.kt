package com.example.avatr.ui.viewmodels

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.data.ImageRepository
import com.example.avatr.data.StableDiffusionRepository
import com.example.avatr.ui.AvatrApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

sealed interface HomeScreenUiState {
    data class Success(val image: String) : HomeScreenUiState // Holds the generated image (Base64 or URL)
    data class Error(val error: String) : HomeScreenUiState // Represents an error state
    data object Loading : HomeScreenUiState // Represents the loading state
    data object NoRequest : HomeScreenUiState // Represents the initial state with no request made
}

class HomeScreenViewModel(private val stableDiffusionRepository: StableDiffusionRepository, private val imageRepository: ImageRepository): ViewModel() {

    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.NoRequest)
        private set

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun generateImage(prompt: String, negativePrompt : String? = null) {

        viewModelScope.launch{
            homeScreenUiState = HomeScreenUiState.Loading // Update state to loading
            homeScreenUiState = try {
                val response = withContext(Dispatchers.IO) {
                    stableDiffusionRepository.generateImageFromText(
                        prompt = prompt, negativePrompt = negativePrompt
                    )
                }
                HomeScreenUiState.Success(response.image)
            }catch (e: IOException) {
                HomeScreenUiState.Error("Incorrect Parameters bro ")
            } catch (e: retrofit2.HttpException) {
                if(e.code() == 402) {
                    HomeScreenUiState.Error("Payment required nigga!")
                }
                else {
                    HomeScreenUiState.Error("Network Issues, try again")
                }
            }
        }
    }

    fun convertBase64ToBitmap(base64String: String): Bitmap {
        val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    fun saveImageToGallery(bitmapString: String) {
        val bitmap = convertBase64ToBitmap(bitmapString)
        viewModelScope.launch {
            imageRepository.SaveImageToGallery(bitmap)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AvatrApplication)
                val stableDiffusionRepository = application.container.stableDiffusionRepository
                val imageRepository = application.container.imageRepository
                HomeScreenViewModel(stableDiffusionRepository = stableDiffusionRepository, imageRepository = imageRepository)
            }
        }
    }
}