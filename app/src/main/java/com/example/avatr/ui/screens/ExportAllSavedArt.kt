package com.example.avatr.ui.screens

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avatr.R
import com.example.avatr.ui.components.CustomTopAppBar
import com.example.avatr.ui.navigation.NavigationDestination

object ExportAllSavedArtDestination : NavigationDestination {
    override val route = "exportAll"
    override val titleRes = R.string.export_saved_art
}

@Composable
fun ExportAllScreen(
    navigateBack: () -> Unit
) {
    ExportAllBody(navigateBack)
}

@Composable
private fun ExportAllBody(
    navigateBack: () -> Unit
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
                .fillMaxHeight(0.9f)
                .padding(top = dimensionResource(R.dimen.extra_large_padding), bottom = dimensionResource(R.dimen.large_padding), start = dimensionResource(R.dimen.large_padding), end = dimensionResource(R.dimen.large_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.button_height))
        ) {
            CustomTopAppBar(title = R.string.export_saved_art, navigateBack = navigateBack)

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.extra_large_padding))
            ) {
                Icon(imageVector = ImageVector.vectorResource(R.drawable.export_saved_art_icon_large), contentDescription = null, modifier = Modifier.size(80.dp), tint = MaterialTheme.colorScheme.tertiary)
                
                Text(text = stringResource(R.string.download_all), color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)

                Button(imageVector = ImageVector.vectorResource(R.drawable.save_icon), text = R.string.export_as_zip)
            }
        }
    }
}

@Composable
private fun Button(imageVector: ImageVector, text: Int) {
    androidx.compose.material3.Button(
        onClick = { /* Handle click */ },
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