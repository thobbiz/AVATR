package com.example.avatr.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.ui.viewmodels.CollectionsScreenViewModel
import com.example.avatr.ui.viewmodels.DeleteAllSavedArtViewModel
import com.example.avatr.ui.viewmodels.HomeScreenViewModel
import com.example.avatr.ui.viewmodels.SavedImageViewModel

object AvatrViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            CollectionsScreenViewModel(avatrApplication().container.savedPhotoRepository)
        }

        initializer {
            HomeScreenViewModel(
                avatrApplication().container.stableDiffusionRepository,
                avatrApplication().container.imageRepository,
                avatrApplication().container.savedPhotoRepository,
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
    }
}

fun CreationExtras.avatrApplication(): AvatrApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AvatrApplication)