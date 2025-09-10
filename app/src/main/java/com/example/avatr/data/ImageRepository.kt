package com.example.avatr.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class ImageRepository(private val context: Context) {
    private val title = "Generated Image"

    suspend fun saveImageToGallery(bitmap: Bitmap?): Uri? {
        if (bitmap == null) return null

        return withContext(Dispatchers.IO) {
            val contentResolver = context.contentResolver
            val imageUrl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$title.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            }
            try {
                val uri = contentResolver.insert(imageUrl, contentValues)
                if (uri != null) {
                    contentResolver.openOutputStream(uri).use { outputStream ->
                        if (outputStream != null) {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        }
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val pendingValues = ContentValues().apply {
                            put(MediaStore.Images.Media.IS_PENDING, 0)
                        }
                        contentResolver.update(uri, pendingValues, null, null)
                    }
                }
                uri
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }
}
