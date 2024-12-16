package com.example.avatr.ui.screens

import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.avatr.R
import com.example.avatr.data.SavedPhoto
import com.example.avatr.ui.components.CustomButton2
import com.example.avatr.ui.components.CustomNavBar
import com.example.avatr.ui.components.CustomTopAppBar
import com.example.avatr.ui.components.loadImageFromStorage

@Composable
fun SavedImageScreen(
    savedPhoto: SavedPhoto,
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateBack: () -> Unit
) {
    SavedImageBody(savedPhoto, navigateToHome, navigateToCollections, navigateToSettings, navigateBack)
}

@Composable
private fun SavedImageBody(
    savedPhoto: SavedPhoto,
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateBack: () -> Unit
) {
    val navController = rememberNavController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, bottom = 0.dp, end = 15.dp, top = 40.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {

            CustomTopAppBar(title = R.string.saved_image, navigateBack = navigateBack)

            FirstColumn(savedPhoto)

            HorizontalDivider(color = Color(0xffdfe0e0))


            SecondColumn()
        }

        CustomNavBar(navController, navigateToHome, navigateToCollections, navigateToSettings)

    }
}

@Composable
private fun FirstColumn(
    savedPhoto: SavedPhoto,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.Start
    ) {
        val bitmap = loadImageFromStorage(savedPhoto.base64FilePath)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .border(2.dp, Color.Transparent),
            shape = RoundedCornerShape(5.dp)
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Generated Image",
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        Text(text = savedPhoto.prompt, style = MaterialTheme.typography.labelLarge)
        Text(text = savedPhoto.date, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
private fun SecondColumn() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
       Row(
           horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
       ){
           Icon(painterResource(R.drawable.twitter_icon), contentDescription = "twitter", tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(24.dp))
           Text(text = "Share on Twitter", style = MaterialTheme.typography.labelSmall)
       }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {
            Icon(painterResource(R.drawable.remove_collections), contentDescription = "remove from collection", tint = MaterialTheme.colorScheme.tertiary,  modifier = Modifier.size(24.dp))
            Text(text = "Remove from Collection", style = MaterialTheme.typography.labelSmall)
        }
        CustomButton2(imageVector = ImageVector.vectorResource(R.drawable.save_icon), text = R.string.save_to_device, action = {})
    }
}