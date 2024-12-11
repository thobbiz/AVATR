package com.example.avatr.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    title: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = dimensionResource(R.dimen.small_padding)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clickable { /* handle click here */ }
        ) {
            IconButton(onClick = navigateBack, modifier = Modifier.size(18.dp)) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrowback_icon),
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text( stringResource(title), style =  MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
    }
}