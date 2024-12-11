package com.example.avatr.ui.components

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.avatr.R

@Composable
fun CustomNavBar(
    navController: NavController,
    navigateToHome: () -> Unit = {},
    navigateToCollections: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
) {
    val items = listOf(
        NavigationItem( "Home", R.drawable.home_icon, R.drawable.home_icon_clicked, navigateToHome),
        NavigationItem( "Collections", R.drawable.collections_icon, R.drawable.collection_icon_clicked , navigateToCollections),
        NavigationItem( "Settings", R.drawable.settings_icon, R.drawable.setting_icon_clicked, navigateToSettings)
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route

    Log.e("why", currentScreen.toString())

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
                .height(45.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = item.label == currentBackStackEntry.toString()
                    Icon(
                        painter = painterResource(
                            if (isSelected) item.iconClicked else item.icon
                        ),
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Gray,
                        modifier = Modifier
                            .padding(4.dp)
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
    val iconClicked: Int,
    val navigateTo: () -> Unit
)