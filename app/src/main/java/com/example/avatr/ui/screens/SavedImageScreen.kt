package com.example.avatr.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avatr.R
import com.example.avatr.data.SavedPhoto
import com.example.avatr.ui.AvatrViewModelProvider
import com.example.avatr.ui.components.CustomButton2

import com.example.avatr.ui.components.CustomTopAppBar
import com.example.avatr.ui.components.ShareToTweeter
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
    navigateBack: () -> Unit
) {
    SavedImageBody(navigateBack)
}

@Composable
private fun SavedImageBody(
    navigateBack: () -> Unit,
    viewModel: SavedImageViewModel = viewModel(factory = AvatrViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(top = dimensionResource(R.dimen.extra_large_padding), bottom = dimensionResource(R.dimen.large_padding), start = dimensionResource(R.dimen.large_padding), end = dimensionResource(R.dimen.large_padding)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CustomTopAppBar(title = R.string.saved_image, navigateBack = navigateBack)
            FirstColumn(uiState.value.savedPhotoDetails.toSavedPhoto())
            HorizontalDivider(color = MaterialTheme.colorScheme.onSurfaceVariant)
            SecondColumn(
                viewModel = viewModel,
                savedPhoto = uiState.value.savedPhotoDetails.toSavedPhoto(),
                onDeleteConfirm = {
                    coroutineScope.launch {
                        viewModel.deleteSavedPhoto()
                        navigateBack()
                    }
                                  },
                context = LocalContext.current
            )
        }
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
        Text(text = savedPhoto.prompt, style = MaterialTheme.typography.labelLarge, lineHeight = 30.sp)
        Text(text = "Saved on: ${savedPhoto.date}", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.tertiary)
    }
}

@Composable
private fun SecondColumn(
    savedPhoto: SavedPhoto,
    onDeleteConfirm: () -> Unit,
    viewModel: SavedImageViewModel,
    context: Context
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp),
        horizontalAlignment = Alignment.Start
    ) {
        SavedImageFunction(
            text = R.string.share_on_twitter,
            icon = R.drawable.twitter_icon,
            action = {
                ShareToTweeter(context, savedPhoto.base64FilePath)
            })

        SavedImageFunction(
            text = R.string.remove_from_collection,
            icon = R.drawable.remove_collections,
            action = { deleteConfirmationRequired = true })

        CustomButton2(
            imageVector = ImageVector.vectorResource(R.drawable.save_icon),
            text = R.string.save_to_device,
            action = {
                viewModel.saveImageToGallery(savedPhoto.base64FilePath)
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
            })

        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDeleteConfirm()
                    Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                },
                onDeleteCancel = { deleteConfirmationRequired = false })
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        title = { Text("Delete this Collection?", style = MaterialTheme.typography.bodyMedium) },
        text = { Text("This cannot be undone.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Delete", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
            }
        })
}

@Composable
private fun SavedImageFunction(text: Int, icon: Int, action: () -> Unit) {
    Card(onClick = action, colors = CardDefaults.cardColors(containerColor = Color.Transparent), modifier = Modifier.padding(0.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painterResource(icon), contentDescription = "remove from collection", tint = MaterialTheme.colorScheme.tertiary,  modifier = Modifier.size(24.dp))
            Text(text = stringResource(text), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondary)
        }
    }
}