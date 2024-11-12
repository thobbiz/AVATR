package com.example.avatr.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avatr.R

@Composable
fun CustomNavBar(
    navController: NavController,
    navigateToHome: () -> Unit = {},
    navigateToCollections: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
) {

    val items = listOf(
        NavigationItem( "Home", R.drawable.home_icon, navigateToHome),
        NavigationItem( "Collections", R.drawable.collections_icon, navigateToCollections),
        NavigationItem( "Settings", R.drawable.settings_icon, navigateToSettings)
    )

    val currentRoute by remember {
        derivedStateOf { navController.currentBackStackEntry?.destination?.route}
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Transparent), // Transparent background for the outer box
        contentAlignment = Alignment.Center
    ) {
        // Pebble-like rounded background
        Surface(
            color = Color.Black,
            shape = RoundedCornerShape(30.dp), // Creates the rounded "pebble" effect
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.45f)
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = item.label == currentRoute
                    Icon(
                        painter = painterResource(
                            item.icon
                        ),
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Gray,
                        modifier = Modifier
                            .clickable {
                                item.navigateTo()
                            }
                    )
                }
            }
        }
    }
}

data class NavigationItem(
    val label:String,
    val icon: Int,
    val navigateTo: () -> Unit
)