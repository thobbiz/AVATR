package com.example.avatr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import com.example.avatr.R
import com.example.avatr.ui.components.CustomButton

@Composable
fun OnBoardingScreen(images1: List<Int>,images2: List<Int>) {
        OnBoardingBody(images1 = images1, images2 = images2)
}

@Composable
private fun OnBoardingBody(images1: List<Int>,images2: List<Int>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ImageList1(image1 = images1[0], image2 = images1[1])
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.images_padding)))
            ImageList2(image1 = images2[0], image2 = images2[1], image3 = images2[2])
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(alignment = Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.99f),
                            MaterialTheme.colorScheme.primary
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                // AVATR Logo and Text
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.icon_text_bottom_padding))
                ) {

                    Icon(
                        painter = painterResource(R.drawable.splash_icon),
                        contentDescription = "Avatr Logo",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.splash_icon_size))
                    )
                    Text(
                        text = "Bring Your Art Ideas to Life\nwith Generative Art AI Models",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                //Get Started Button
               CustomButton(R.string.get_started)
            }
        }
    }
}

@Composable
private fun ImageList1(image1: Int, image2: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
        ,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.images_padding))
    ) {

        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.small_image_width))
                .height(dimensionResource(id = R.dimen.small_image_height))
                .weight(2f)
        ) {
            Image(
                painter = painterResource(image1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.small_image_height))
                    .clip(RectangleShape)
                    .fillMaxSize()
            )

        }
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.small_image_width))
                .height(dimensionResource(id = R.dimen.small_image_height))
                .weight(2.5f)
        ) {
            Image(
                painter = painterResource(image2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RectangleShape)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun ImageList2(image1: Int,  image2: Int, image3: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.images_padding))
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.large_image_width))
                .height(dimensionResource(id = R.dimen.large_image_height))
                .weight(1f)
        ) {
            Image(
                painter = painterResource(image1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.large_image_height))
                    .clip(RectangleShape)
                    .fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.large_image_width))
                .height(dimensionResource(id = R.dimen.large_image_height))
                .weight(1.5f)
        ) {
            Image(
                painter = painterResource(image2),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.large_image_height))
                    .fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.large_image_width))
                .height(dimensionResource(id = R.dimen.large_image_height))
                .weight(0.5f)
        ) {
            Image(
                painter = painterResource(image3),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(dimensionResource(R.dimen.large_image_height))
                    .fillMaxSize()
            )
        }
    }
}