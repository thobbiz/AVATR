package com.example.avatr.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val AI_MODEL = intPreferencesKey("ai_model")
        const val TAG = "UserPreferencesRepo"
    }

    val aiModel: Flow<Int> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[AI_MODEL] ?: 0
    }

    suspend fun saveAiModel(index: Int) {
        dataStore.edit { preferences ->
            preferences[AI_MODEL] = index
        }
    }
}