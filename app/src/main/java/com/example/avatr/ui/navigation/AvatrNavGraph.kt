package com.example.avatr.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.avatr.ui.screens.CollectionsScreen
import com.example.avatr.ui.screens.DeleteAllSavedArtScreen
import com.example.avatr.ui.screens.ExportAllScreen
import com.example.avatr.ui.screens.HomeScreen
import com.example.avatr.ui.screens.SavedImageScreen
import com.example.avatr.ui.screens.SettingsScreen
import kotlinx.coroutines.CoroutineScope

enum class AvatrScreen(val route: String) {
    Home("Home"),
    Collections("Collections"),
    Settings("Settings"),
    Export("Export"),
    Delete("Delete"),
    SavedPhoto("Saved Photo")
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AvatrNavHost(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    NavHost(
        startDestination = AvatrScreen.Home.route,
        navController = navController,
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) }
    ) {
        composable(AvatrScreen.Home.route) {
            HomeScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                drawerState = drawerState
            )
        }
        composable(route = AvatrScreen.Collections.route) {
            CollectionsScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                scope = scope,
                drawerState = drawerState
            )
        }
        composable(route = AvatrScreen.Settings.route) {
            SettingsScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                scope = scope,
                drawerState = drawerState,
                navigateToExport = { navController.navigateTo(AvatrScreen.Export) },
                navigateToDelete = { navController.navigateTo(AvatrScreen.Delete) },
            )
        }
        composable(route = AvatrScreen.Export.route) {
            ExportAllScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = AvatrScreen.Delete.route) {
            DeleteAllSavedArtScreen(
                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
//
//        composable(route = AvatrScreen.SavedPhoto.route) {
//            SavedImageScreen(
//                savedPhoto = ,
//                navigateToHome = { navController.navigateTo(AvatrScreen.Home) },
//                navigateToCollections = { navController.navigateTo(AvatrScreen.Collections) },
//                navigateToSettings = { navController.navigateTo(AvatrScreen.Settings) },
//                navigateBack = {
//                    navController.navigateUp()
//                }
//            )
//        }
    }
}

fun NavHostController.navigateTo(screen: AvatrScreen) {
    this.navigate(screen.route) {
        launchSingleTop = true
        popUpTo(screen.route) { inclusive = true }
        restoreState = true
    }
}

@Composable
fun currentScreen(navController: NavHostController): AvatrScreen? {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    Log.e("why now", AvatrScreen.values().find{ it.route == currentRoute }.toString())
    return AvatrScreen.values().find{ it.route == currentRoute }
}