package com.example.avatr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.avatr.ui.screens.CollectionsScreen
import com.example.avatr.ui.screens.HomeScreen
import com.example.avatr.ui.screens.SettingsScreen


enum class AvatrScreen() {
    Home,
    Collections,
    Settings
}

@Composable
fun AvatrNavHost(
    navController: NavHostController,
) {
    NavHost(
        startDestination = AvatrScreen.Home.name,
        navController = navController
    ) {
        composable(AvatrScreen.Home.name) {
            HomeScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name)},
                navigateToCollections = {navController.navigate(AvatrScreen.Collections.name)},
                navigateToSettings = {navController.navigate(AvatrScreen.Settings.name)},
            )
        }

        composable(route = AvatrScreen.Collections.name) {
            CollectionsScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name)},
                navigateToCollections = { navController.navigate(AvatrScreen.Collections.name)},
                navigateToSettings = { navController.navigate(AvatrScreen.Settings.name) }
            )
        }

        composable(route = AvatrScreen.Settings.name) {
            SettingsScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name) },
                navigateToCollections = { navController.navigate(AvatrScreen.Collections.name) },
                navigateToSettings = { navController.navigate(AvatrScreen.Settings.name)}
            )
        }
    }
}