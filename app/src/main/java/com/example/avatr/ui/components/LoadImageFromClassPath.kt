package com.example.avatr.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun loadImageFromStorage(filePath: String): Bitmap? {
    val file = File(filePath)
    return if (file.exists()) {
        BitmapFactory.decodeFile(filePath)
    } else {
        null
    }
}