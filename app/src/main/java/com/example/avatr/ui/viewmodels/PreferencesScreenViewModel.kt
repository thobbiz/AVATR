package com.example.avatr.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.data.AIModel
import com.example.avatr.data.UserPreferencesRepository
import com.example.avatr.ui.AvatrApplication
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PreferencesScreenViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    application: Application
): AndroidViewModel(application) {

    val uiState: StateFlow<PreferencesUiState> =
        userPreferencesRepository.aiModel.map { ai_model ->
            PreferencesUiState(ai_model)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = runBlocking {
                PreferencesUiState(
                    aiModel = userPreferencesRepository.aiModel.first()
                )
            }
        )
    private fun getCurrentAIModel(): AIModel {
        return when (uiState.value.aiModel) {
            0 -> AIModel.STABLE_DIFFUSION
            1 -> AIModel.BLACK_FOREST_FLUX
            else -> AIModel.STABLE_DIFFUSION
        }
    }

    fun getCurrentAIModelName(): String {
        val aiModel = getCurrentAIModel()
        return aiModel.displayName
    }

    fun selectAIModel(index: Int) {
        viewModelScope.launch {
            userPreferencesRepository.saveAiModel(index)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AvatrApplication)
                val userPreferencesRepository = application.userPreferencesRepository
                PreferencesScreenViewModel(userPreferencesRepository = userPreferencesRepository, application = application)
            }
        }
    }
}


data class PreferencesUiState(
    val aiModel: Int = 0,
)