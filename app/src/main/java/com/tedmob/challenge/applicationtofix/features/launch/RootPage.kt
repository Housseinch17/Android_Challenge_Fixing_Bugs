package com.tedmob.challenge.applicationtofix.features.launch

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tedmob.challenge.applicationtofix.features.authentication.LoginPage
import com.tedmob.challenge.applicationtofix.features.authentication.RegisterPage

@Composable
fun RootPage(
    onRedirectToMain: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController, RootRoute.Splash) {
        composable<RootRoute.Splash> {
            SplashPage(
                onRedirectToMain = onRedirectToMain,
                onRedirectToLogin = {
                    navController.navigate(RootRoute.Login) { popUpTo<RootRoute.Splash> { inclusive = true } }
                },
            )
        }

        composable<RootRoute.Login> {
            LoginPage(
                onRedirectToMain = onRedirectToMain,
                onRedirectToRegister = {
                    navController.navigate(RootRoute.Register)
                },
            )
        }

        composable<RootRoute.Register> {
            RegisterPage(
                onRedirectToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}