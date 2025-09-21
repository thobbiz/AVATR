package com.example.avatr.ui.components

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun ShareToTweeter(imageName: String) {
    val context = LocalContext.current
    val imageFile = File(context.filesDir.toString(), "Pictures/$imageName")
    val contentUri = FileProvider.getUriForFile(
        context,
        "com.example.avatr.fileprovider",
        imageFile
    )

    val tweetIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, contentUri)
        type = "image/jpg"
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