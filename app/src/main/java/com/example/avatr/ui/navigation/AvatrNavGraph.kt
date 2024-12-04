package com.example.avatr.ui.navigation

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
                navigateToHome = {
                    navController.navigate(AvatrScreen.Home.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Collections.name) {
                            inclusive = true
                        }
                    }
                },

                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Settings.name) {
                            inclusive = true
                        }
                    }
                },
                scope = scope,
                drawerState = drawerState
            )
        }

        composable(route = AvatrScreen.Collections.name) {
            CollectionsScreen(
                navigateToHome = {
                    navController.navigate(AvatrScreen.Home.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Collections.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Settings.name) {
                            inclusive = true
                        }
                    }
                },
                scope = scope,
                drawerState = drawerState
            )
        }

        composable(route = AvatrScreen.Settings.name) {
            SettingsScreen(
                navigateToHome = {
                    navController.navigate(AvatrScreen.Home.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Collections.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Settings.name) {
                            inclusive = true
                        }
                    }
                },
                scope = scope,
                drawerState = drawerState,
                navigateToExport = {
                    navController.navigate(AvatrScreen.Export.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Export.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToDelete = {
                    navController.navigate(AvatrScreen.Delete.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Delete.name) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(route = AvatrScreen.Export.name) {
            ExportAllScreen(
                navigateToHome = {
                    navController.navigate(AvatrScreen.Home.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Collections.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Settings.name) {
                            inclusive = true
                        }
                    }
                },

                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(route = AvatrScreen.Delete.name) {
            DeleteAllSavedArtScreen(
                navigateToHome = {
                    navController.navigate(AvatrScreen.Home.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Home.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToCollections = {
                    navController.navigate(AvatrScreen.Collections.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Collections.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToSettings = {
                    navController.navigate(AvatrScreen.Settings.name) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(AvatrScreen.Settings.name) {
                            inclusive = true
                        }
                    }
                },

                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}