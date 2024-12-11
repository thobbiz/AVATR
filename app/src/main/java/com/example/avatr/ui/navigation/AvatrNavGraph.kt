package com.example.avatr.ui.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.avatr.ui.screens.CollectionsScreen
import com.example.avatr.ui.screens.DeleteAllSavedArtScreen
import com.example.avatr.ui.screens.ExportAllScreen
import com.example.avatr.ui.screens.HomeScreen
import com.example.avatr.ui.screens.SettingsScreen
import kotlinx.coroutines.CoroutineScope

enum class AvatrScreen {
    Home,
    Collections,
    Settings,
    Export,
    Delete
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AvatrNavHost(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
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
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                drawerState = drawerState
            )
        }
        composable(route = AvatrScreen.Collections.name) {
            CollectionsScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                scope = scope,
                drawerState = drawerState
            )
        }
        composable(route = AvatrScreen.Settings.name) {
            SettingsScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                scope = scope,
                drawerState = drawerState,
                navigateToExport = { navController.navigateTo(AvatrScreen.Export) },
                navigateToDelete = { navController.navigate(AvatrScreen.Delete) },
            )
        }
        composable(route = AvatrScreen.Export.name) {
            ExportAllScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = AvatrScreen.Delete.name) {
            DeleteAllSavedArtScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavHostController.navigateTo(screen: AvatrScreen) {
    this.navigate(screen.name) {
        launchSingleTop = true
        restoreState = true
        popUpTo(screen.name) { inclusive = true }
    }
}