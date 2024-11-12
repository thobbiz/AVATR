package com.example.avatr.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
        navController = navController,
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) }
    ) {
        composable(
            AvatrScreen.Home.name
        ) {
            HomeScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Home.name) {
                        inclusive = true
                    }
                } },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Collections.name) {
                        inclusive = true
                    }
                } },

                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Settings.name) {
                        inclusive = true
                    } }
                }
            )
        }

        composable(route = AvatrScreen.Collections.name) {
            CollectionsScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Home.name) {
                        inclusive = true
                    }
                } },
                navigateToCollections = {navController.navigate(AvatrScreen.Collections.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Collections.name) {
                        inclusive = true
                    }
                } },
                navigateToSettings = {navController.navigate(AvatrScreen.Settings.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Settings.name) {
                        inclusive = true
                    }
                } },
            )
        }

        composable(route = AvatrScreen.Settings.name) {
            SettingsScreen(
                navigateToHome = { navController.navigate(AvatrScreen.Home.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Home.name) {
                        inclusive = true
                    }
                } },
                navigateToCollections = {navController.navigate(AvatrScreen.Collections.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Collections.name) {
                        inclusive = true
                    }
                } },
                navigateToSettings = {navController.navigate(AvatrScreen.Settings.name) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(AvatrScreen.Settings.name) {
                        inclusive = true
                    }
                } },
            )
        }
    }
}