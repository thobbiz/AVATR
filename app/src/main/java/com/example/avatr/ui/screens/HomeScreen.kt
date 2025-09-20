package com.example.avatr.ui.screens

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.avatr.R
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.navigation.navigateTo
import com.example.avatr.ui.viewmodels.AuthState
import com.example.avatr.ui.viewmodels.AuthViewModel
import com.example.avatr.ui.viewmodels.HomeScreenUiState
import com.example.avatr.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.activity.result.PickVisualMediaRequest
import kotlin.io.encoding.ExperimentalEncodingApi

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeScreen(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
   HomeBody(drawerState = drawerState, modifier = modifier, navController = navController, authViewModel = authViewModel)
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
private fun HomeBody(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.Factory)
    var promptText by rememberSaveable { mutableStateOf("") }
    var negativePromptText by rememberSaveable { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val isSheetOpen = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()

    val onGenerate: (Uri?) -> Unit = { uri ->
        viewModel.onGenerate(
            context = context,
            prompt = promptText,
            negativePrompt = negativePromptText,
            uri = uri
        )
    }


    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navController.navigateTo(LoginDestination)
            else -> Unit
        }
    }

    LaunchedEffect(viewModel.homeScreenUiState) {
        if (viewModel.homeScreenUiState is HomeScreenUiState.Success) {
            isSheetOpen.value = true
        }
    }

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
                .padding(
                    top = dimensionResource(R.dimen.extra_large_padding),
                    bottom = dimensionResource(R.dimen.large_padding),
                    start = dimensionResource(R.dimen.large_padding),
                    end = dimensionResource(R.dimen.large_padding)
                ),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {
            CustomHeader(scope = scope, headerText = R.string.home_screen_header, drawerState = drawerState)
            ImageContainer(
                viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f),
            )
            DescriptionTextField(
                text = promptText,
                onTextChange = { newText -> promptText = newText },
                onGenerate = onGenerate
            )
            AdvancedOptions(
                text = negativePromptText,
                onTextChange = { newText -> negativePromptText = newText }
            )

            if(isSheetOpen.value) {
                BottomSheet(context = context, sheetState = sheetState, scope = scope, viewModel = viewModel, isSheetOpen = isSheetOpen)
            }
        }
    }
}

@Composable
private fun DescriptionTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onGenerate: (Uri?) -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val darkMode = isSystemInDarkTheme()
    var imageUri by remember{ mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        imageUri = uri
    }


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
            IconButton(onClick = {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }, modifier = Modifier
                .size(30.dp)
                .padding(0.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.upload_icon),
                    modifier = Modifier.size(25.dp),
                    contentDescription = "upload",
                    tint = if (isFocused || (imageUri != null)) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                )
            }
        },
        trailingIcon = {
            IconButton(onClick = { onGenerate(imageUri) }, modifier = Modifier
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
            .fillMaxWidth()
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
        val bitmap = viewModel.decodeImage(image)
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                modifier = Modifier.fillMaxSize(),
                contentDescription = "Generated Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
    error: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(error, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .fillMaxHeight(0.6f),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
private fun EmptyScreen(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
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

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = stringResource(R.string.no_generated_image),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff747b82),
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
@Composable
private fun AdvancedOptions(
    text: String,
    onTextChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) -0f else 180f, label = ""
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

            IconButton(onClick = { expanded = !expanded},
                Modifier
                    .size(20.dp)
                    .rotate(rotationAngle)) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
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

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheet(
    context: Context,
    sheetState: SheetState,
    scope: CoroutineScope,
    viewModel: HomeScreenViewModel,
    isSheetOpen: MutableState<Boolean>
) {
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
                        Toast.makeText(context, "Saved to Collection!", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
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
