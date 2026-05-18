package com.tedmob.challenge.applicationtofix.features.home

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed interface MainRoute {

    @Serializable
    data object Home : MainRoute

    @Serializable
    data object Breeds : MainRoute

    @Serializable
    data object Profile : MainRoute

    @Serializable
    data class BreedDetails(
        val id: String,
    ) : MainRoute

    @Serializable
    data object Settings : MainRoute

    //...
}

val NavBackStackEntry.currentMainRoute: MainRoute?
    get() = when {
        destination.hasRoute<MainRoute.Home>() -> toRoute<MainRoute.Home>()
        destination.hasRoute<MainRoute.Breeds>() -> toRoute<MainRoute.Breeds>()
        destination.hasRoute<MainRoute.Profile>() -> toRoute<MainRoute.Profile>()
        destination.hasRoute<MainRoute.BreedDetails>() -> toRoute<MainRoute.BreedDetails>()
        destination.hasRoute<MainRoute.Settings>() -> toRoute<MainRoute.Settings>()
        //...

        else -> null
    }