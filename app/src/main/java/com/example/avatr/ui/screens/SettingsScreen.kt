package com.example.avatr.ui.screens

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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.avatr.R
import com.example.avatr.ui.components.CustomHeader
import com.example.avatr.ui.components.CustomNavBar


@Composable
fun SettingsScreen(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
    ) {
    SettingsBody(navigateToHome = navigateToHome, navigateToCollections = navigateToCollections, navigateToSettings = navigateToSettings)
}

@Composable
private fun SettingsBody(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

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
                FirstColumn()
                HorizontalDivider()
                SecondColumn()
            }
        }
        CustomNavBar(navController, navigateToHome, navigateToCollections, navigateToSettings)
    }
}

@Composable
private fun FirstColumn() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        FirstColumnFirstRowBlueprint(imageVector = R.drawable.ai_model_icon, text = R.string.ai_model)
        FirstColumnSecondRowBlueprint(imageVector = R.drawable.language_icon, text = R.string.language)
    }
}

@Composable
private fun SecondColumn() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SecondColumnBlueprint(imageVector = R.drawable.export_saved_art_icon, text = R.string.export_saved_art)
        SecondColumnBlueprint(imageVector = R.drawable.delete_saved_art_icon, text = R.string.delete_saved_art)
    }
}


@Composable
private fun FirstColumnFirstRowBlueprint(imageVector: Int, text: Int) {
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.stable_diffusion_model), color = MaterialTheme.colorScheme.onTertiary, fontWeight = FontWeight.Bold)

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(18.dp)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_down),
                        contentDescription = stringResource(text),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(18.dp)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_up),
                        contentDescription = stringResource(text),
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
        }

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
private fun SecondColumnBlueprint(imageVector: Int, text: Int) {
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
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(18.dp)) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.arrowfront_icon),
                contentDescription = stringResource(text),
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }
}