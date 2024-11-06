package com.example.avatr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.avatr.ui.screens.HomeScreen
import com.example.avatr.ui.screens.ImageContainer
import com.example.avatr.ui.screens.ModelSelectionScreen
import com.example.avatr.ui.theme.AvatrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvatrTheme {
                val images1 = listOf(
                    R.drawable._1,
                    R.drawable._2
                )

                val images2 = listOf(
                    R.drawable._3,
                    R.drawable._4,
                    R.drawable._5
                )
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
//                    ModelSelectionScreen(model1 = R.string.stable_diffusion_runway_ml, model2 = R.string.waifu_diffusion_hakurei, modifier = Modifier
//                        .fillMaxSize()
//                        .padding(
//                            dimensionResource(id = R.dimen.large_padding)
//                        ))

                    HomeScreen(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}