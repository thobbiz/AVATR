package com.example.avatr.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Saved Photos")
data class SavedPhoto (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val prompt: String,
    val filePath: String,
    val model: String,
    val date: String
)