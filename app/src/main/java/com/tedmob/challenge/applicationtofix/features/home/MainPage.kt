package com.tedmob.challenge.applicationtofix.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tedmob.challenge.applicationtofix.features.breeds.BreedDetailsPage
import com.tedmob.challenge.applicationtofix.features.breeds.BreedsPage
import com.tedmob.challenge.applicationtofix.features.profile.ProfilePage
import com.tedmob.challenge.applicationtofix.features.settings.SettingsPage

@Composable
fun MainPage(
    onRestartApp: () -> Unit,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute by remember {
        derivedStateOf { currentBackStackEntry?.currentMainRoute ?: MainRoute.Home }
    }

    Column(
        Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController,
            MainRoute.Home,
            Modifier
                .consumeWindowInsets(
                    WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)
                )
                .weight(1f)
                .fillMaxWidth(),
        ) {
            composable<MainRoute.Home> {
                HomePage(
                    onRedirectToProfile = {
                        navController.navigate(MainRoute.Profile)
                    },
                )
            }

            composable<MainRoute.Breeds> {
                BreedsPage(
                    onSelectedBreed = {
                        navController.navigate(MainRoute.BreedDetails(it.id))
                    },
                )
            }

            composable<MainRoute.Profile> {
                ProfilePage(
                    onSettings = {
                        navController.navigate(MainRoute.Settings)
                    },
                )
            }

            composable<MainRoute.BreedDetails> {
                BreedDetailsPage()
            }

            composable<MainRoute.Settings> {
                SettingsPage(
                    onRestartApp = onRestartApp,
                )
            }
        }

        MainBottomNavComponent(
            currentRoute,
            onRouteSelected = {
                if (it == currentRoute) {
                    return@MainBottomNavComponent
                }

                if (it is MainRoute.Home) {
                    navController.popBackStack(MainRoute.Home, false)
                } else {
                    navController.navigate(it) { popUpTo(MainRoute.Home) { inclusive = false } }
                }
            },
            Modifier.fillMaxWidth(),
        )
    }
}