package com.example.avatr.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File


fun ShareToTweeter(context: Context, filePath: String) {
    val imageFile = File(filePath)
    val contentUri = FileProvider.getUriForFile(
        context,
        "com.example.avatr.fileprovider",
        imageFile
    )

    val tweetIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, contentUri)
        putExtra(Intent.EXTRA_TEXT, "Check out my AVATR-generated image 🚀 #JetpackCompose")
        type = "image/jpeg"
        setPackage("com.twitter.android")
    }
    tweetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    try {
        context.startActivity(tweetIntent)
    } catch (e: Exception) {
        val fallback = Intent.createChooser(tweetIntent, "Share via")
        context.startActivity(fallback)
    }
}