package com.example.avatr.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
) {

    NavHost(
        startDestination = HomeDestination.route,
        navController = navController,
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
                navigateToSavedImage = {
                    navController.navigate("${SavedImageDestination.route}/$it")
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
                navigateToExport = { navController.navigateTo(ExportAllSavedArtDestination) },
                navigateToDelete = { navController.navigateTo(DeleteAllSavedArtDestination) },
                drawerState = drawerState,
                scope = scope
            )
        }
        composable(
            route = ExportAllSavedArtDestination.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
        ) {
            ExportAllScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = DeleteAllSavedArtDestination.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            }
            ) {
            DeleteAllSavedArtScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = SavedImageDestination.routeWithArgs,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { it / 2 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            arguments = listOf(navArgument(SavedImageDestination.savedPhotoIdArg) {
                type = NavType.IntType
            })
        ) {
            SavedImageScreen(
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
//        graph.startDestinationRoute?.let {
//            popUpTo(it) {
//                inclusive = false
//                saveState = true
//            }
//        }
//        restoreState = true
    }
}


@Composable
fun NavHostController.currentScreen(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}