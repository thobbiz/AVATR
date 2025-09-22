package com.example.avatr

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.avatr.data.SavedPhoto
import com.example.avatr.data.SavedPhotoDao
import com.example.avatr.data.SavedPhotosDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SavedPhotosDaoTest {
    private lateinit var savedPhotoDao: SavedPhotoDao
    private lateinit var savedPhotosDatabase: SavedPhotosDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        savedPhotosDatabase = Room.inMemoryDatabaseBuilder(context, SavedPhotosDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        savedPhotoDao = savedPhotosDatabase.savedPhotoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        savedPhotosDatabase.close()
    }

    private var savedPhoto1 = SavedPhoto(1, "Apples", "data/", "20/5/21")
    private var savedPhoto2 = SavedPhoto(2, "Mango", "data/", "20/5/25")

    private suspend fun addOnePhotoToDb() {
        savedPhotoDao.insert(savedPhoto1)
    }

    private suspend fun addTwoPhotosToDb() {
        savedPhotoDao.insert(savedPhoto1)
        savedPhotoDao.insert(savedPhoto2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsSavedPhotoIntoDB() = runBlocking {
        addOnePhotoToDb()
        val allSavedPhotos = savedPhotoDao.getAllSavedPhotos().first()
        assertEquals(allSavedPhotos[0], savedPhoto1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllSavedPhotos_returnsAllSavedPhotosFromDB() = runBlocking {
        addTwoPhotosToDb()
        val allSavedPhotos = savedPhotoDao.getAllSavedPhotos().first()
        assertEquals(allSavedPhotos[0], savedPhoto2)
        assertEquals(allSavedPhotos[1], savedPhoto1)
    }
}