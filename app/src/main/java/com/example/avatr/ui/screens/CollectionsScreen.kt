package com.example.avatr.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.avatr.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun CollectionsScreen(scope: CoroutineScope, drawerState: DrawerState) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
            ) {

                Header(scope = scope, headerText = R.string.collections, drawerState = drawerState)

                Text(
                    text = stringResource(R.string.art_you_ve_saved_will_appear_here),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xff747b82)
                )
            }

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
                    color = Color(0xff747b82)
                )
            }

        }

        BottomNavigationBar()
    }
}