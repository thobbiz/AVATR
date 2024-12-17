package com.example.avatr.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.avatr.R
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.rotate
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.components.CustomNavBar
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.viewmodels.HomeScreenUiState
import com.example.avatr.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeScreen(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToPreferences: () -> Unit,
    drawerState: DrawerState
) {
   HomeBody(navigateToHome = navigateToHome, navigateToCollections = navigateToCollections, navigateToPreferences = navigateToPreferences, drawerState = drawerState)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
private fun HomeBody(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToPreferences: () -> Unit,
    drawerState: DrawerState
) {
    val viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory)
    val navController = rememberNavController()
    var promptText by rememberSaveable { mutableStateOf("") }
    var negativePromptText by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val isSheetOpen = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(viewModel.homeScreenUiState) {
        if (viewModel.homeScreenUiState is HomeScreenUiState.Success) {
            isSheetOpen.value = true
        }
    }

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
            CustomHeader(scope = scope, headerText = R.string.home_screen_header, drawerState = drawerState)
            ImageContainer(viewModel, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f))

            DescriptionTextField(
                text = promptText,
                onTextChange = { newText -> promptText = newText },
                onGenerate = {
                    if (promptText.isBlank()) {
                    Log.w("GenerateImage", "Prompt text is empty!")
                    } else {
                    viewModel.generateImage(promptText, negativePromptText)
                    }
                }
            )
            AdvancedOptions(
                text = negativePromptText,
                onTextChange = { newText -> negativePromptText = newText }
            )
        }
        CustomNavBar(navController, navigateToHome, navigateToCollections, navigateToPreferences)

        //Bottom Sheet
        if(isSheetOpen.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                    isSheetOpen.value = false
                },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.primary
            )
            {
                Column(
                    modifier = Modifier.padding(horizontal = 25.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(40.dp),
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = {
                                viewModel.savePhotoToCollection()
                                scope.launch {
                                    sheetState.hide()
                                }
                                isSheetOpen.value = false
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painterResource(R.drawable.collections_icon),
                                    contentDescription = "Save To Collection",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Save To Collection",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painterResource(R.drawable.twitter_icon),
                                    contentDescription = "twitter",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = stringResource(R.string.share_on_twitter),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    viewModel.saveImageToGallery(
                                        (viewModel.homeScreenUiState as HomeScreenUiState.Success).image
                                    )
                                }
                            }
                            isSheetOpen.value = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
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
                            Icon(imageVector = ImageVector.vectorResource(id = R.drawable.save_icon), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                            Text(stringResource(R.string.save_to_device), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DescriptionTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onGenerate: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val darkMode = isSystemInDarkTheme()

    OutlinedTextField(
        value = text,
        onValueChange = {newText -> onTextChange(newText)},
        placeholder = { Text(stringResource(R.string.describe_your_ai_masterpiece),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a), fontWeight = FontWeight.Bold) },
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            cursorColor = MaterialTheme.colorScheme.onPrimary
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier
                .size(30.dp)
                .padding(0.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.upload_icon),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "upload",
                    tint = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                )
            }
        },

        trailingIcon = {
            IconButton(onClick = onGenerate, modifier = Modifier
                .size(50.dp)
                .padding(0.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(if(darkMode) { R.drawable.generate_icon_dark_mode} else { R.drawable.generate_icon_light_mode}),
                    contentDescription = "generate",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
        modifier = Modifier
            .width(450.dp)
            .height(dimensionResource(R.dimen.button_height))
            .border(4.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        enabled = true,
        singleLine = true
    )
}

@Composable
private fun ImageContainer(
    viewModel: HomeScreenViewModel,
    modifier: Modifier
) {
    when(viewModel.homeScreenUiState) {
        is HomeScreenUiState.NoRequest -> EmptyScreen()
        is HomeScreenUiState.Success -> SuccessScreen(viewModel, (viewModel.homeScreenUiState as HomeScreenUiState.Success).image)
        is HomeScreenUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize(), (viewModel.homeScreenUiState as HomeScreenUiState.Error).error)
        else -> LoadingScreen(modifier = Modifier.fillMaxSize())
    }

}

@Composable
private fun SuccessScreen(viewModel: HomeScreenViewModel, image: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        val bitmap = viewModel.convertBase64ToBitmap(image)
        Image(
            bitmap = bitmap.asImageBitmap(),
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun ErrorScreen(modifier: Modifier, error: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text( error, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
            Text("Loading...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun EmptyScreen(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.brush_icon),
                modifier = Modifier.size(80.dp),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(
                text = stringResource(R.string.no_generated_image),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xff747b82),
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Composable
private fun AdvancedOptions(
    text: String,
    onTextChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 0f else 360f
    )

    Column(
        modifier = Modifier
            .animateContentSize (
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentWidth()
        ) {

            Text(
                text = stringResource(R.string.advanced_options),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )

            IconButton(onClick = { expanded = !expanded}, Modifier.size(20.dp).rotate(rotationAngle)) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.advanced_options),
                    tint = Color(0xff494d5a)
                )
            }
        }

        if(expanded) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange ,
                placeholder = { Text(stringResource(R.string.i_dont_wanna_see),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a), fontWeight = FontWeight.Bold) },
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodySmall,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.onPrimary
                ),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ },modifier = Modifier.size(24.dp)) {
                        Icon(
                            painter = painterResource(R.drawable.exclude_icon),
                            contentDescription = "upload",
                            tint = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                        )
                    }
                },
                modifier = Modifier
                    .width(450.dp)
                    .height(dimensionResource(R.dimen.button_height))
                    .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                enabled = true,
                singleLine = true
            )
        }
    }
}