package com.example.avatr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedPhoto::class], version = 1, exportSchema = false)
abstract class SavedPhotosDatabase: RoomDatabase() {
    abstract fun savedPhotoDao(): SavedPhotoDao

    companion object {
        @Volatile
        private var Instance: SavedPhotosDatabase? = null

        fun getDatabase(context: Context): SavedPhotosDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SavedPhotosDatabase::class.java, "saved_photos_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}