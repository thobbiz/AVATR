package com.example.avatr.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.avatr.R
import com.example.avatr.data.AIModel
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.viewmodels.PreferencesScreenViewModel
import kotlinx.coroutines.CoroutineScope

object PreferencesDestination : NavigationDestination {
    override val route = "preferences"
    override val titleRes = R.string.preferences
}


@Composable
fun PreferencesScreen(
    navigateToExport: () -> Unit,
    navigateToDelete: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
    ) {
    PreferencesBody(scope = scope, drawerState = drawerState, navigateToExport = navigateToExport, navigateToDelete = navigateToDelete)
}

@Composable
private fun PreferencesBody(
    navigateToExport: () -> Unit,
    navigateToDelete: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val viewModel: PreferencesScreenViewModel = viewModel(factory = PreferencesScreenViewModel.Factory)
    val uiState = viewModel.uiState.collectAsState().value
    val onModelOptionSelected = viewModel::selectAIModel
    val selectedIndex = uiState.aiModel
    val modelOptions = listOf(AIModel.STABLE_DIFFUSION.displayName, AIModel.BLACK_FOREST_FLUX.displayName)


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

                CustomHeader(scope = scope, headerText = R.string.preferences, drawerState = drawerState)

                Text(
                    text = stringResource(R.string.tweak_settings),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff747b82)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Log.d("ai model", viewModel.getCurrentAIModelName())
                FirstColumn(modelOptions = modelOptions, selectedIndex = selectedIndex,
                    onModelOptionSelected = { index ->
                        onModelOptionSelected(index)
                    }
                )
                HorizontalDivider()
                SecondColumn(
                    navigateToExport = navigateToExport,
                    navigateToDelete = navigateToDelete
                )
            }
        }
    }
}

@Composable
private fun FirstColumn(modelOptions: List<String>, onModelOptionSelected: (Int) -> Unit, selectedIndex: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        FirstColumnFirstRowBlueprint(imageVector = R.drawable.ai_model_icon, text = R.string.ai_model, modelOptions = modelOptions, onModelOptionSelected = onModelOptionSelected, selectedIndex = selectedIndex)
        FirstColumnSecondRowBlueprint(imageVector = R.drawable.language_icon, text = R.string.language)
    }
}

@Composable
private fun SecondColumn(
    navigateToExport: () -> Unit,
    navigateToDelete: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SecondColumnBlueprint(imageVector = R.drawable.export_saved_art_icon_small, text = R.string.export_saved_art, navigateToScreen = navigateToExport)
        SecondColumnBlueprint(imageVector = R.drawable.delete_saved_art_icon_small, text = R.string.delete_saved_art, navigateToScreen = navigateToDelete)
    }
}


@Composable
private fun FirstColumnFirstRowBlueprint(imageVector: Int, text: Int, modelOptions: List<String>, onModelOptionSelected: (Int) -> Unit, selectedIndex: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(imageVector),
                contentDescription = null,
                tint =  MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.bodyMedium,
                color =  MaterialTheme.colorScheme.onSecondary,
                fontWeight = FontWeight.Bold
            )
        }
        ModelSelector(modelOptions, selectedIndex, onModelOptionSelected)
    }
}

@Composable
private fun FirstColumnSecondRowBlueprint(imageVector: Int, text: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(imageVector),
                contentDescription = null,
                tint =  MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
        ) {
            Text(text = stringResource(R.string.english), color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold)

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(18.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrowfront_icon),
                    contentDescription = stringResource(text),
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}

@Composable
private fun SecondColumnBlueprint(imageVector: Int, text: Int, navigateToScreen: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(imageVector),
                contentDescription = null,
                tint =  MaterialTheme.colorScheme.onSecondary
            )

            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        IconButton(onClick = navigateToScreen, modifier = Modifier.size(18.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrowfront_icon),
                contentDescription = stringResource(text),
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}

@Composable
fun ModelSelector(
    modelOptions: List<String>,
    selectedIndex: Int,
    onModelOptionSelected: (Int) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = modelOptions[selectedIndex],
            color = MaterialTheme.colorScheme.onTertiary,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            IconButton(
                onClick = {
                    val newIndex = if (selectedIndex > 0) selectedIndex - 1 else modelOptions.lastIndex
                onModelOptionSelected(newIndex)
                          },
                modifier = Modifier.size(18.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_down),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }

            IconButton(onClick = {
                val newIndex = if (selectedIndex < modelOptions.lastIndex) selectedIndex + 1 else 0
                onModelOptionSelected(newIndex)
            }, modifier = Modifier.size(18.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_up),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}