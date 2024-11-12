package com.example.avatr

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.avatr.ui.screens.SideMenuScreen
import com.example.avatr.ui.theme.AvatrTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AvatrTheme {
//                val images1 = listOf(
//                    R.drawable._1,
//                    R.drawable._2
//                )
//
//                val images2 = listOf(
//                    R.drawable._3,
//                    R.drawable._4,
//                    R.drawable._5
//                )
                Surface(modifier = Modifier.fillMaxSize()) {
                    SideMenuScreen()
                }
            }
        }
    }
}