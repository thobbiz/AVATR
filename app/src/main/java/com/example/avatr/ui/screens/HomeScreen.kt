package com.example.avatr.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.avatr.R
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.components.CustomNavBar
import com.example.avatr.ui.viewmodels.HomeScreenUiState
import com.example.avatr.ui.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HomeScreen(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
   HomeBody(navigateToHome = navigateToHome, navigateToCollections = navigateToCollections, navigateToSettings = navigateToSettings, scope = scope, drawerState = drawerState)
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
private fun HomeBody(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    val viewModel = HomeScreenViewModel()
    val navController = rememberNavController()
    var promptText by remember { mutableStateOf("") }
    var negativePromptText by remember { mutableStateOf("") }

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

            ImageContainer(viewModel.homeScreenUiState)

            DescriptionTextField(
                text = promptText,
                onTextChange = { newText -> promptText = newText },
                onGenerate = {
                    if (promptText.isBlank()) {
                    Log.w("GenerateImage", "Prompt text is empty!")
                } else {
                    viewModel.generateImage(promptText, negativePromptText)
                }}
            )

            AdvancedOptions(
                text = negativePromptText,
                onTextChange = { newText -> negativePromptText = newText }
            )
        }
        CustomNavBar(navController, navigateToHome, navigateToCollections, navigateToSettings)
    }
}

@Composable
private fun DescriptionTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onGenerate: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = {newText -> onTextChange(newText)},
        placeholder = { Text(stringResource(R.string.describe_your_ai_masterpiece),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a), fontWeight = FontWeight.Bold) },
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(24.dp)) {
                Icon(
                    painter = painterResource(R.drawable.upload_icon),
                    contentDescription = "upload",
                    tint = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                )
            }
        },

        trailingIcon = {
            IconButton(onClick = onGenerate) {
                Icon(
                    painter = painterResource(R.drawable.generate_icon_button),
                    contentDescription = "generate"
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

@Composable
private fun ImageContainer(
    homeScreenUiState: HomeScreenUiState
) {
 Card(
     modifier = Modifier
         .fillMaxWidth()
         .border(2.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
         .fillMaxHeight(0.6f),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
 ) {

     when(homeScreenUiState) {
         is HomeScreenUiState.NoRequest -> EmptyScreen()
         is HomeScreenUiState.Success -> SuccessScreen(image = homeScreenUiState.image)
         is HomeScreenUiState.Error -> TODO()
         is HomeScreenUiState.Loading -> TODO()
     }

    }

}

@Composable
private fun SuccessScreen(image: String) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = image)
    }
}

@Composable
private fun EmptyScreen(

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

        Text(text = stringResource(R.string.no_generated_image), style = MaterialTheme.typography.bodySmall, color = Color(0xff747b82), fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun AdvancedOptions(
    text: String,
    onTextChange: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

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

            IconButton(onClick = { expanded = !expanded}, Modifier.size(20.dp)) {
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
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
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