package com.example.avatr.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.avatr.ui.screens.CollectionsDestination
import com.example.avatr.ui.screens.CollectionsScreen
import com.example.avatr.ui.screens.DeleteAllSavedArtDestination
import com.example.avatr.ui.screens.DeleteAllSavedArtScreen
import com.example.avatr.ui.screens.ExportAllSavedArtDestination
import com.example.avatr.ui.screens.ExportAllScreen
import com.example.avatr.ui.screens.HomeDestination
import com.example.avatr.ui.screens.HomeScreen
import com.example.avatr.ui.screens.PreferencesDestination
import com.example.avatr.ui.screens.PreferencesScreen
import com.example.avatr.ui.screens.SavedImageDestination
import com.example.avatr.ui.screens.SavedImageScreen
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AvatrNavHost(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope
) {
    NavHost(
        startDestination =HomeDestination.route,
        navController = navController,
        enterTransition = { fadeIn(tween(500)) },
        exitTransition = { fadeOut(tween(500)) }
    ) {
        composable(
            HomeDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
            ) {
            HomeScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                drawerState = drawerState
            )
        }
        composable(
            route = CollectionsDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            CollectionsScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                navigateToSavedImage = {
                    navController.navigate("${SavedImageDestination.route}/$it")
                    Log.d("SavedImage", "Navigating with ID: $it")
                },
                scope = scope,
                drawerState = drawerState
            )
        }
        composable(
            route = PreferencesDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            PreferencesScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                navigateToExport = { /*TODO*/ },
                navigateToDelete = { /*TODO*/ },
                drawerState = drawerState,
                scope = scope
            )
        }
        composable(
            route = ExportAllSavedArtDestination.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { 300 }) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { -300 }) + fadeOut()
            }
        ) {
            ExportAllScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = DeleteAllSavedArtDestination.route,
            enterTransition = {
                slideInVertically(initialOffsetY = { 300 }) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { -300 }) + fadeOut()
            }
            ) {
            DeleteAllSavedArtScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = SavedImageDestination.routeWithArgs,
            enterTransition = {
                slideInVertically(initialOffsetY = { 300 }) + fadeIn()
            },
            exitTransition = {
                slideOutVertically(targetOffsetY = { -300 }) + fadeOut()
            },
            arguments = listOf(navArgument(SavedImageDestination.savedPhotoIdArg) {
                type = NavType.IntType
            })
        ) {
            SavedImageScreen(
                navigateToHome = { navController.navigateTo(HomeDestination) },
                navigateToCollections = { navController.navigateTo(CollectionsDestination) },
                navigateToPreferences = { navController.navigateTo(PreferencesDestination) },
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

fun NavHostController.navigateTo(navigationDestination: NavigationDestination) {
    this.navigate(navigationDestination.route) {
        launchSingleTop = true
        popUpTo(navigationDestination.route) { inclusive = true }
        restoreState = true
    }
}