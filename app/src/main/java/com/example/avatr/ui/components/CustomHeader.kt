package com.example.avatr.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avatr.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomHeader(scope: CoroutineScope, headerText: Int, drawerState: DrawerState) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
                scope.launch{ drawerState.open() } }
        ) {
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = "menu"
            )
        }
        Text(
            text = stringResource(headerText),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
    }
}