package com.example.avatr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avatr.R
import com.example.avatr.data.SavedPhoto
import com.example.avatr.ui.AvatrViewModelProvider
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.components.loadImageFromStorage
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.viewmodels.CollectionsScreenViewModel
import kotlinx.coroutines.CoroutineScope

object CollectionsDestination : NavigationDestination {
    override val route = "collections"
    override val titleRes = R.string.collections
}

@Composable
fun CollectionsScreen(
    navigateToSavedImage: (Int) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    viewModel: CollectionsScreenViewModel = viewModel(factory = AvatrViewModelProvider.Factory)
    ) {

    val collectionsUiState by viewModel.collectionsUiState.collectAsState()

    CollectionsBody(
        drawerState = drawerState,
        scope = scope,
        savedPhotosList = collectionsUiState.savedPhotosList,
        navigateToSavedImage = navigateToSavedImage
    )
}

@Composable
private fun CollectionsBody(
    savedPhotosList: List<SavedPhoto>,
    navigateToSavedImage: (Int) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
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
                .fillMaxHeight()
                .padding(top = dimensionResource(R.dimen.extra_large_padding), bottom = dimensionResource(R.dimen.large_padding), start = dimensionResource(R.dimen.large_padding), end = dimensionResource(R.dimen.large_padding)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
            ) {

                CustomHeader(scope = scope, headerText = R.string.collections, drawerState = drawerState)

                Text(
                    text = stringResource(R.string.art_you_ve_saved_will_appear_here),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xff747b82)
                )

                SavedPhotosList(savedPhotosList = savedPhotosList, onSavedPhotoClick = navigateToSavedImage)
            }
        }
    }
}

@Composable
private fun EmptyScreen() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(R.drawable.empty_icon), contentDescription = null)
        Text(
            text = "You haven’t saved any art yet",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xff747b82)
        )
    }
}

@Composable
private fun SavedPhotosList(
    savedPhotosList: List<SavedPhoto>,
    onSavedPhotoClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {

    if(savedPhotosList.isEmpty()) {
        EmptyScreen()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            itemsIndexed(savedPhotosList) { _, savedPhoto ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .clickable { onSavedPhotoClick(savedPhoto.id) }
                        .border(
                            1.5.dp,
                            MaterialTheme.colorScheme.outlineVariant,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        loadImageFromStorage(savedPhoto.base64FilePath)?.let {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = "Generated Image",
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = savedPhoto.prompt,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSecondary,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(0.9f)
                            )

                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.arrowfront_icon),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
}