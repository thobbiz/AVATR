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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avatr.R
import com.example.avatr.data.SavedPhoto
import com.example.avatr.ui.AvatrViewModelProvider
import com.example.avatr.ui.components.CustomButton2
import com.example.avatr.ui.components.CustomNavBar
import com.example.avatr.ui.components.CustomTopAppBar
import com.example.avatr.ui.components.loadImageFromStorage
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.viewmodels.SavedImageViewModel
import com.example.avatr.ui.viewmodels.toSavedPhoto
import kotlinx.coroutines.launch

object SavedImageDestination : NavigationDestination {
    override val route = "saved_image"
    override val titleRes = R.string.saved_image
    const val savedPhotoIdArg = "savedPhotoId"
    val routeWithArgs = "$route/{$savedPhotoIdArg}"
}

@Composable
fun SavedImageScreen(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToPreferences: () -> Unit,
    navigateBack: () -> Unit
) {
    SavedImageBody(navigateToHome, navigateToCollections, navigateToPreferences, navigateBack)
}

@Composable
private fun SavedImageBody(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToPreferences: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SavedImageViewModel = viewModel(factory = AvatrViewModelProvider.Factory)
) {
    val navController = rememberNavController()
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {

            CustomTopAppBar(title = R.string.saved_image, navigateBack = navigateBack)

            FirstColumn(uiState.value.savedPhotoDetails.toSavedPhoto())

            HorizontalDivider(color = Color(0xffdfe0e0))


            SecondColumn(viewModel = viewModel, savedPhoto = uiState.value.savedPhotoDetails.toSavedPhoto(), onDeleteConfirm = {
                coroutineScope.launch {
                    viewModel.deleteSavedPhoto()
                    navigateBack()
                }
            })
        }

        CustomNavBar(navController, navigateToHome, navigateToCollections, navigateToPreferences)

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
                .fillMaxHeight(0.55f)
                .clip(RoundedCornerShape(4.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            shape = RoundedCornerShape(4.dp)
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
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
private fun SecondColumn(
    savedPhoto: SavedPhoto,
    onDeleteConfirm: () -> Unit,
    viewModel: SavedImageViewModel
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Card(onClick = { /*TODO*/ }, colors = CardDefaults.cardColors(containerColor = Color.Transparent), modifier = Modifier.padding(0.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(painterResource(R.drawable.twitter_icon), contentDescription = "twitter", tint = MaterialTheme.colorScheme.tertiary, modifier = Modifier.size(24.dp))
                Text(text = "Share on Twitter", style = MaterialTheme.typography.labelSmall)
            }
        }
        Card(onClick = { deleteConfirmationRequired = true }, colors = CardDefaults.cardColors(containerColor = Color.Transparent), modifier = Modifier.padding(0.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(R.drawable.remove_collections), contentDescription = "remove from collection", tint = MaterialTheme.colorScheme.tertiary,  modifier = Modifier.size(24.dp))
                Text(text = "Remove from Collection", style = MaterialTheme.typography.labelSmall)
            }
        }
        CustomButton2(imageVector = ImageVector.vectorResource(R.drawable.save_icon), text = R.string.save_to_device, action = {viewModel.saveImageToGallery(savedPhoto.base64FilePath)})
    }

    if(deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteConfirm()
            },
            onDeleteCancel = { deleteConfirmationRequired = false })
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        containerColor = MaterialTheme.colorScheme.primary,
        title = { Text("Attention!", style = MaterialTheme.typography.bodyMedium) },
        text = { Text("Are you sure you want to Delete this Collection ?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("No", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        })
}