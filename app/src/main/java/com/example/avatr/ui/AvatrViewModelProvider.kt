package com.example.avatr.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.avatr.ui.viewmodels.CollectionsScreenViewModel
import com.example.avatr.ui.viewmodels.HomeScreenViewModel

object AvatrViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = this[AndroidViewModelFactory.APPLICATION_KEY] as AvatrApplication
            CollectionsScreenViewModel(app.container.savedPhotoRepository)
        }

        initializer {
            val app = this[AndroidViewModelFactory.APPLICATION_KEY] as AvatrApplication
            HomeScreenViewModel(
                app.container.stableDiffusionRepository,
                app.container.imageRepository,
                app.container.savedPhotoRepository,
                application = app
            )

        }
    }
}

fun CreationExtras.avatrApplication(): AvatrApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as AvatrApplication)