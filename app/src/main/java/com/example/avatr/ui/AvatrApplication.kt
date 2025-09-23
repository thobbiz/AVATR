package com.example.avatr.ui

import com.example.avatr.data.AppContainer
import com.example.avatr.data.DefaultAppContainer
import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.avatr.data.UserPreferencesRepository


private const val AI_MODEL_PREFERENCE_NAME = "ai_model_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = AI_MODEL_PREFERENCE_NAME
)

class AvatrApplication: Application() {
    lateinit var container: AppContainer
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
        container = DefaultAppContainer(context = applicationContext)
    }
}