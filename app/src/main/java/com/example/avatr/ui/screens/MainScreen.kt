package com.example.avatr.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.avatr.ui.components.CustomNavBar
import com.example.avatr.ui.components.DrawerContent
import com.example.avatr.ui.navigation.AvatrNavHost
import com.example.avatr.ui.navigation.currentScreen
import com.example.avatr.ui.navigation.navigateTo
import com.example.avatr.ui.viewmodels.AuthState
import com.example.avatr.ui.viewmodels.AuthViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainScreen(
    isDarkTheme: MutableState<Boolean>
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val authState = authViewModel.authState.observeAsState()

    val startDestination = when (authState.value) {
        is AuthState.Authenticated -> HomeDestination.route
        else -> OnBoardingDestination.route
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        ModalNavigationDrawer(
            gesturesEnabled = true,
            drawerState = drawerState,
            drawerContent = {
                DrawerContent(scope = scope, drawerState = drawerState, isDarkTheme = isDarkTheme)
            }
        ) {
            Scaffold(
                bottomBar = {
                    navController.currentScreen()?.let {
                        if(navController.currentScreen() != "exportAll" && navController.currentScreen() != "deleteAll" && navController.currentScreen() != "signup" && navController.currentScreen() != "login" && navController.currentScreen() != "modelselection" && navController.currentScreen() != "onboarding") {
                            CustomNavBar(
                                currentScreen = it,
                                navigateToHome = { navController.navigateTo(HomeDestination) },
                                navigateToPreferences = {
                                    navController.navigateTo(
                                        PreferencesDestination
                                    )
                                },
                                navigateToCollections = {
                                    navController.navigateTo(
                                        CollectionsDestination
                                    )
                                }
                            )
                        }
                    }
                }
            ) { innerPadding ->
                AvatrNavHost(navController, authViewModel, drawerState, scope, Modifier.padding(innerPadding), startDestination = startDestination)
            }
        }
    }
}