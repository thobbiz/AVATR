package com.example.avatr.ui.navigation

import android.R
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.avatr.ui.screens.LoginDestination
import com.example.avatr.ui.screens.LoginScreen
import com.example.avatr.ui.screens.ModelSelectionDestination
import com.example.avatr.ui.screens.ModelSelectionScreen
import com.example.avatr.ui.screens.OnBoardingDestination
import com.example.avatr.ui.screens.OnBoardingScreen
import com.example.avatr.ui.screens.PreferencesDestination
import com.example.avatr.ui.screens.PreferencesScreen
import com.example.avatr.ui.screens.SavedImageDestination
import com.example.avatr.ui.screens.SavedImageScreen
import com.example.avatr.ui.screens.SignUpDestination
import com.example.avatr.ui.screens.SignUpScreen
import com.example.avatr.ui.viewmodels.AuthState
import com.example.avatr.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AvatrNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    NavHost(
        startDestination = startDestination,
        navController = navController,
    ) {

        composable (
            route = OnBoardingDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            OnBoardingScreen(
                modifier, navController, authViewModel
            )
        }

        composable (
            route = ModelSelectionDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            ModelSelectionScreen(
                modifier, navController, authViewModel
            )
        }

        composable (
            route = LoginDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            LoginScreen(
                modifier, navController, authViewModel
            )
        }

        composable (
            route = SignUpDestination.route,
            enterTransition = {
                fadeIn(animationSpec = tween(durationMillis = 300))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(durationMillis = 300))
            }
        ) {
            SignUpScreen(
                modifier, navController, authViewModel,
            )
        }

        composable(
            route = HomeDestination.route,
            ) {
            HomeScreen(
                drawerState = drawerState,
                navController = navController,
                modifier = modifier,
                authViewModel = authViewModel
            )
        }
        composable(
            route = CollectionsDestination.route,
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
                slideInHorizontally (
                    initialOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally (
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
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
                slideInHorizontally (
                    initialOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally (
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
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
                slideInHorizontally (
                    initialOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally (
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearEasing
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
    }
}


@Composable
fun NavHostController.currentScreen(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}