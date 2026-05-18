package com.tedmob.challenge.applicationtofix.features.launch

import kotlinx.serialization.Serializable

sealed interface RootRoute {

    data object Splash : RootRoute

    @Serializable
    data object Login : RootRoute

    @Serializable
    data object Register : RootRoute
}