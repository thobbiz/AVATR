package com.example.avatr.ui.viewmodels

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avatr.network.StableDiffusionApiImpl
import com.example.avatr.network.TextToImageRequest
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeScreenUiState {
    data class Success(val image: String) : HomeScreenUiState // Holds the generated image (Base64 or URL)
    data object Error : HomeScreenUiState // Represents an error state
    data object Loading : HomeScreenUiState // Represents the loading state
    data object NoRequest : HomeScreenUiState // Represents the initial state with no request made
}
private const val TAG = "HomeScreenViewModel"

class HomeScreenViewModel() : ViewModel() {

    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.NoRequest)
        private set

    private val apiService = StableDiffusionApiImpl()

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun generateImage(promptText: String, negativePromptText : String? = null) {
        viewModelScope.launch{
            homeScreenUiState = HomeScreenUiState.Loading // Update state to loading
            homeScreenUiState = try {
                val response = apiService.generateImage(TextToImageRequest(promptText, negativePromptText))
                Log.i(TAG, "Noooooo Error1")
                HomeScreenUiState.Success("Success ${response.image}")
            }catch (e: IOException) {
                Log.e(TAG, "Noooooo Error1")
                HomeScreenUiState.Error
            } catch (e: HttpException) {
                Log.e(TAG, "Noooooo Error2")
                HomeScreenUiState.Error
            }
        }
    }
}