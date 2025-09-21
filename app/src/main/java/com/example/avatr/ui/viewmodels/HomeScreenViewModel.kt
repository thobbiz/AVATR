package com.example.avatr.ui.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.data.ImageRepository
import com.example.avatr.data.SavedPhoto
import com.example.avatr.data.SavedPhotosRepository
import com.example.avatr.data.StableDiffusionRepository
import com.example.avatr.ui.AvatrApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

sealed interface HomeScreenUiState {
    data class Success(val image: String) : HomeScreenUiState
    data class Error(val error: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object NoRequest : HomeScreenUiState
}

class HomeScreenViewModel(
    private val stableDiffusionRepository: StableDiffusionRepository,
    private val imageRepository: ImageRepository,
    private val savedPhotosRepository: SavedPhotosRepository,
    application: Application
): AndroidViewModel(application) {

    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.NoRequest)
        private set

    private var latestPrompt: String? = null
    private var latestGeneratedImage: String? = null
    private val _decodedBitmap = MutableStateFlow<Bitmap?>(null)
    val decodedBitmap: StateFlow<Bitmap?> = _decodedBitmap

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @RequiresApi(Build.VERSION_CODES.O)
    fun onGenerate(
        context: Context,
        prompt: String,
        negativePrompt: String?,
        uri: Uri?
    ) {
        viewModelScope.launch {
            if (uri == null) {
                generateImageFromText(prompt, negativePrompt)
            } else {
                val imagePart = prepareImagePart(context, uri)
                Log.d("image-part", imagePart.toString())
                generateImageFromImage(prompt, negativePrompt, imagePart)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun generateImageFromText(prompt: String, negativePrompt : String? = null) {
        viewModelScope.launch{
            homeScreenUiState = HomeScreenUiState.Loading // Update state to loading
            homeScreenUiState = try {
                val response = withContext(Dispatchers.IO) {
                    stableDiffusionRepository.generateImageFromText(
                        prompt = prompt, negativePrompt = negativePrompt
                    )
                }
                latestPrompt = prompt
                latestGeneratedImage = response.image
                HomeScreenUiState.Success(response.image)
            }catch (e: IOException) {
                HomeScreenUiState.Error("Incorrect Parameters bro ")
            } catch (e: retrofit2.HttpException) {
                if(e.code() == 402) {
                    HomeScreenUiState.Error("Payment required. Please update your subscription.")
                }
                else {
                    HomeScreenUiState.Error("Network Issues, try again")
                }
            }
        }
    }

    fun generateImageFromImage(prompt: String, negativePrompt: String? = null, imagePart: MultipartBody.Part){
        viewModelScope.launch {
            homeScreenUiState = HomeScreenUiState.Loading
            homeScreenUiState = try {
                val response = withContext(Dispatchers.IO) {
                    stableDiffusionRepository.generateImageFromImage(
                        prompt = prompt, negativePrompt = negativePrompt, image = imagePart
                    )
                }
                latestPrompt = prompt
                latestGeneratedImage = response.image
                HomeScreenUiState.Success(response.image)
            } catch (e: retrofit2.HttpException) {
                HomeScreenUiState.Error(
                    when (e.code()) {
                        400 -> "Invalid Parameters."
                        402 -> "Payment required.\n Please update your subscription."
                        403 -> "Your Content was flagged"
                        413 -> "Your request was too large"
                        else -> "Invalid"
                    }
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun savePhotoToCollection() {
        CoroutineScope(Dispatchers.IO).launch {
            val prompt = latestPrompt
            val base64 = latestGeneratedImage
            if(prompt != null && base64 != null) {
                viewModelScope.launch {
                    val savedPhoto = SavedPhoto(
                        prompt = prompt,
                        base64FilePath = saveImageToStorage(context = getApplication(), bitmap = convertBase64ToBitmap(base64), prompt = prompt),
                        date = displayDate(),
                        id = 0
                    )
                    savedPhotosRepository.insertSavedPhoto(savedPhoto)
                }
            }
        }
    }

    fun saveImageToGallery(bitmapString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = convertBase64ToBitmap(bitmapString)
            imageRepository.saveImageToGallery(bitmap)
        }
    }

    private suspend fun saveImageToStorage(context: Context, bitmap: Bitmap?, prompt: String): String {
        return withContext(Dispatchers.IO) {
            val directory = File(context.filesDir, "images")
            if (!directory.exists()) {
                directory.mkdirs()
            }
            val file = File(directory, "${prompt}_${System.currentTimeMillis()}.jpg")
            FileOutputStream(file).use { out ->
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            file.absolutePath
        }
    }

    private fun prepareImagePart(context: Context, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: throw IllegalStateException("Cannot open input stream")
        val fileName = "upload_${System.currentTimeMillis()}.png"

        val requestBody = inputStream.readBytes()
            .toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "image",
            fileName,
            requestBody
        )
    }


    private suspend fun convertBase64ToBitmap(base64String: String): Bitmap? = withContext(Dispatchers.Default) {
        try {
            val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun decodeImage(image: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val bitmap = convertBase64ToBitmap(image)
            _decodedBitmap.value = bitmap
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayDate(): String {
        val currentDate = LocalDate.now()
        val dayOfMonth = currentDate.dayOfMonth
        val monthAndYear = currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.getDefault()))
        val ordinalSuffix = getOrdinalSuffix(dayOfMonth)
        val formattedDate = "$dayOfMonth$ordinalSuffix $monthAndYear"

        return formattedDate
    }

    private fun getOrdinalSuffix(day: Int): String {
        return when {
            day in 11..13 -> "th"
            day % 10 == 1 -> "st"
            day % 10 == 2 -> "nd"
            day % 10 == 3 -> "rd"
            else -> "th"
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AvatrApplication)
                val stableDiffusionRepository = application.container.stableDiffusionRepository
                val savedPhotosRepository = application.container.savedPhotoRepository
                val imageRepository = application.container.imageRepository
                HomeScreenViewModel(stableDiffusionRepository = stableDiffusionRepository, imageRepository = imageRepository, savedPhotosRepository = savedPhotosRepository, application = application)
            }
        }
    }
}