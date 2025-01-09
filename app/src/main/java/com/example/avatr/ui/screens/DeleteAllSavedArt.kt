package com.example.avatr.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avatr.R
import com.example.avatr.ui.AvatrViewModelProvider
import com.example.avatr.ui.components.CustomTopAppBar
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.viewmodels.DeleteAllSavedArtViewModel
import kotlinx.coroutines.launch

object DeleteAllSavedArtDestination : NavigationDestination {
    override val route = "deleteAll"
    override val titleRes = R.string.delete_saved_art
}

@Composable
fun DeleteAllSavedArtScreen(
    navigateBack: () -> Unit,
) {
    DeleteAllSavedArtBody(navigateBack)
}

@Composable
private fun DeleteAllSavedArtBody(
    navigateBack: () -> Unit,
    viewModel: DeleteAllSavedArtViewModel = viewModel(factory = AvatrViewModelProvider.Factory)
) {

    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.extra_large_padding))
        ) {

            CustomTopAppBar(title = R.string.delete_saved_art, navigateBack = navigateBack)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_large_padding))
            ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.delete_saved_art_icon_large), contentDescription = null,  modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.tertiary)

                Text(text = stringResource(R.string.delete_all),  color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)

                Button(
                    imageVector = ImageVector.vectorResource(R.drawable.trash_icon),
                    text = R.string.delete_everything,
                    action = {
                        deleteConfirmationRequired = true
                    }
                )

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            coroutineScope.launch {
                                viewModel.DeleteAllSavedArt()
                            }
                            Toast.makeText(context, "All Saved Art have been Deleted", Toast.LENGTH_SHORT).show()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false })
                }
            }
        }
    }
}

@Composable
private fun Button(imageVector: ImageVector, text: Int, action: () -> Unit) {
    androidx.compose.material3.Button(
        onClick = action,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.images_padding))
        ) {

            Icon(imageVector = imageVector, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Text(stringResource(text), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)

        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        containerColor = MaterialTheme.colorScheme.primary,
        title = { Text("Attention!", style = MaterialTheme.typography.bodyMedium) },
        text = { Text("Are you sure you want to delete ALL your Saved Art ?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary) },
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