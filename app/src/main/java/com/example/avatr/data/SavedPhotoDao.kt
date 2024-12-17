package com.example.avatr.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedPhotoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedPhoto: SavedPhoto)

    @Delete
    suspend fun delete(savedPhoto: SavedPhoto)

    @Query("SELECT * from `Saved Photos` WHERE id = :id")
    fun getSavedPhoto(id: Int): Flow<SavedPhoto>

    @Query("SELECT * from `Saved Photos` ORDER BY id DESC")
    fun getAllSavedPhotos(): Flow<List<SavedPhoto>>

    @Query("SELECT * from `Saved Photos` WHERE prompt = :prompt")
    fun searchByPrompt(prompt: String): Flow<SavedPhoto>
}