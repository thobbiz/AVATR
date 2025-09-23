package com.example.avatr.ui.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
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
import com.example.avatr.data.AIModel
import com.example.avatr.data.ImageRepository
import com.example.avatr.data.ModelsRepository
import com.example.avatr.data.SavedPhoto
import com.example.avatr.data.SavedPhotosRepository
import com.example.avatr.data.UserPreferencesRepository
import com.example.avatr.ui.AvatrApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    data class Success(val image: ByteArray) : HomeScreenUiState
    data class Error(val error: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object NoRequest : HomeScreenUiState
}

class HomeScreenViewModel(
    private val modelsRepository: ModelsRepository,
    private val imageRepository: ImageRepository,
    private val savedPhotosRepository: SavedPhotosRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    application: Application
): AndroidViewModel(application) {

    var homeScreenUiState: HomeScreenUiState by mutableStateOf(HomeScreenUiState.NoRequest)
        private set

    private var latestPrompt: String? = null
    private var latestGeneratedImage: ByteArray? = null

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
            homeScreenUiState = HomeScreenUiState.Loading
            homeScreenUiState = try {
                val response = withContext(Dispatchers.IO) {
                    modelsRepository.GenerateImageFromText(
                        url = getAIModelUrl(),
                        prompt = prompt,
                        negativePrompt = negativePrompt
                    )
                }
                val bytes = response.bytes()
                latestPrompt = prompt
                latestGeneratedImage = bytes
                bytes.let { HomeScreenUiState.Success(it) }
            }catch (e: IOException) {
                Log.d("error:",  e.toString())
                HomeScreenUiState.Error("Incorrect Parameters bro ")
            } catch (e: retrofit2.HttpException) {
                if(e.code() == 402) {
                    Log.d("error:",  e.toString())
                    HomeScreenUiState.Error("Payment required.\nPlease update your subscription.")
                }
                else {
                    Log.d("error:",  e.toString())
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
                    modelsRepository.sdGenerateImageFromImage(
                        prompt = prompt, negativePrompt = negativePrompt, image = imagePart
                    )
                }
                val bytes = response.body()?.bytes()
                latestPrompt = prompt
                latestGeneratedImage = bytes
                bytes?.let { HomeScreenUiState.Success(it) } ?: HomeScreenUiState.Error("An error occurred")
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
        val prompt = latestPrompt
        val raw = latestGeneratedImage
        if(prompt != null && raw != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val savedPhoto = SavedPhoto(
                    prompt = prompt,
                    filePath = saveImageToStorage(context = getApplication(), bitmap = convertRAWToBitmap(raw), prompt = prompt),
                    model = getAIModelName(),
                    date = displayDate(),
                    id = 0
                )
                savedPhotosRepository.insertSavedPhoto(savedPhoto)
            }
        }
    }

    fun saveImageToGallery(raw: ByteArray) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = convertRAWToBitmap(raw)
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
                val bmp = bitmap ?: throw IllegalArgumentException("Bitmap conversion failed")
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            file.absolutePath
        }
    }

    private fun prepareImagePart(context: Context, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: throw IllegalStateException("Cannot open input stream")
        val fileName = "upload_${System.currentTimeMillis()}.png"

        val requestBody = inputStream.readBytes().toRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(
            "image",
            fileName,
            requestBody
        )
    }


    private suspend fun convertRAWToBitmap(imageBytes: ByteArray): Bitmap? = withContext(Dispatchers.Default) {
        try {
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        } catch (e: IllegalArgumentException) {
            Log.d("error:",  e.toString())
            null
        }
    }

    private fun getAIModel(): AIModel {
        return when (aiModelState.value.aiModel) {
            0 -> AIModel.STABLE_DIFFUSION
            1 -> AIModel.BLACK_FOREST_FLUX
            else -> AIModel.STABLE_DIFFUSION
        }
    }

    private fun getAIModelUrl() :String {
        return getAIModel().postUrl
    }

    private fun getAIModelName() :String {
        return getAIModel().displayName
    }

    fun decodeImage(raw: ByteArray) {
        viewModelScope.launch(Dispatchers.Default) {
            val bitmap = convertRAWToBitmap(raw)
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

    val aiModelState: StateFlow<AiModelUiState> =
        userPreferencesRepository.aiModel.map { ai_model ->
            AiModelUiState(ai_model)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = runBlocking {
                AiModelUiState(
                    aiModel = userPreferencesRepository.aiModel.first()
                )
            }
        )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AvatrApplication)
                val modelsRepository = application.container.modelsRepository
                val savedPhotosRepository = application.container.savedPhotoRepository
                val imageRepository = application.container.imageRepository
                val userPreferencesRepository = application.userPreferencesRepository
                HomeScreenViewModel(modelsRepository = modelsRepository, imageRepository = imageRepository, savedPhotosRepository = savedPhotosRepository, userPreferencesRepository = userPreferencesRepository, application = application)
            }
        }
    }
}

data class AiModelUiState(
    val aiModel: Int = 0,
)