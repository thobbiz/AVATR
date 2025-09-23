package com.example.avatr.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.ui.viewmodels.AuthViewModel
import com.example.avatr.ui.viewmodels.CollectionsScreenViewModel
import com.example.avatr.ui.viewmodels.DeleteAllSavedArtViewModel
import com.example.avatr.ui.viewmodels.HomeScreenViewModel
import com.example.avatr.ui.viewmodels.PreferencesScreenViewModel
import com.example.avatr.ui.viewmodels.SavedImageViewModel

object AvatrViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            AuthViewModel()
        }

        initializer {
            CollectionsScreenViewModel(avatrApplication().container.savedPhotoRepository)
        }

        initializer {
            HomeScreenViewModel(
                avatrApplication().container.modelsRepository,
                avatrApplication().container.imageRepository,
                avatrApplication().container.savedPhotoRepository,
                userPreferencesRepository = avatrApplication().userPreferencesRepository,
                application = avatrApplication()
            )
        }

        initializer {
            SavedImageViewModel(
                this.createSavedStateHandle(),
                avatrApplication().container.savedPhotoRepository,
                avatrApplication().container.imageRepository
            )
        }

        initializer {
            DeleteAllSavedArtViewModel(
                avatrApplication().container.savedPhotoRepository
            )
        }

        initializer {
            PreferencesScreenViewModel(
                avatrApplication().userPreferencesRepository,
                application = avatrApplication()
            )
        }
    }
}

fun CreationExtras.avatrApplication(): AvatrApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AvatrApplication)