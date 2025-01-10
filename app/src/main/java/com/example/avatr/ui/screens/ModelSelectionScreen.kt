package com.example.avatr.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.avatr.R
import com.example.avatr.ui.components.CustomButton1
import com.example.avatr.ui.navigation.NavigationDestination
import com.example.avatr.ui.navigation.navigateTo
import com.example.avatr.ui.viewmodels.AuthState
import com.example.avatr.ui.viewmodels.AuthViewModel

object ModelSelectionDestination : NavigationDestination {
    override val route = "modelselection"
    override val titleRes = R.string.model_selection
}

@Composable
fun ModelSelectionScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val model1 = R.string.stable_diffusion_runway_ml
    val model2 = R.string.waifu_diffusion_hakurei
    ModelOptionBody(
        model1 = model1,
        model2 = model2,
        navController = navController,
        authViewModel = authViewModel
    )
}

@Composable
private fun ModelOptionBody(
    model1: Int,
    model2: Int,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel
    ) {
    var selectedCardIndex by remember { mutableIntStateOf(-1) }

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigateTo(HomeDestination)
            else -> Unit
        }
    }

    Column (
        modifier  = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){

        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_large_padding)),
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.padding(top = 40.dp),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Select AI Model", style = MaterialTheme.typography.displayLarge)
                Text(
                    text = "Select an AI image generation model to continue, you can always change this in preferences.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))
            ) {
                ModelOption1(
                    model = model1,
                    isSelected = selectedCardIndex == 0,
                    onClick = { selectedCardIndex = 0 }
                )

                ModelOption2(model = model2,
                    isSelected = selectedCardIndex == 1,
                    onClick = { selectedCardIndex = 1 }
                )
            }
        }

        CustomButton1(text = R.string.lets_go, imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, action = {navController.navigateTo(LoginDestination)}, enable = true)
    }
}

@Composable
private fun ModelOption1(model: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(width = 1.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
            .width(dimensionResource(id = R.dimen.button_width))
            .height(dimensionResource(id = R.dimen.button_height)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary, contentColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(model),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ModelOption2(model: Int, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
            .width(dimensionResource(id = R.dimen.button_width))
            .height(dimensionResource(id = R.dimen.button_height)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(model),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )

            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}