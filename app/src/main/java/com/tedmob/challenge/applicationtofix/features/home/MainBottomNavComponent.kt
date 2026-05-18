package com.tedmob.challenge.applicationtofix.features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tedmob.challenge.applicationtofix.theme.AppTheme

@Composable
fun MainBottomNavComponent(
    currentRoute: MainRoute,
    onRouteSelected: (route: MainRoute) -> Unit,
    modifier: Modifier = Modifier,
) {
    val routesWhereVisible = remember {
        listOf(
            MainRoute.Home::class,
            MainRoute.Breeds::class,
            MainRoute.Profile::class
        )
    }

    AnimatedVisibility(
        currentRoute::class in routesWhereVisible,
        modifier,
    ) {
        NavigationBar(
            Modifier.fillMaxWidth(),
        ) {
            NavigationBarItem(
                currentRoute is MainRoute.Home,
                onClick = { onRouteSelected(MainRoute.Home) },
                icon = { Icon(Icons.Default.Home, "Home") },
                label = { Text("Home") },
            )
            NavigationBarItem(
                currentRoute is MainRoute.Breeds,
                onClick = { onRouteSelected(MainRoute.Breeds) },
                icon = { Icon(Icons.Default.Info, "Breeds") },
                label = { Text("Breeds") },
            )
            NavigationBarItem(
                currentRoute is MainRoute.Profile,
                onClick = { onRouteSelected(MainRoute.Profile) },
                icon = { Icon(Icons.Default.Person, "Profile") },
                label = { Text("Profile") },
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MainBottomNavComponent_Preview() {
    AppTheme {
        var routeSelected: MainRoute by remember { mutableStateOf(MainRoute.Home) }

        MainBottomNavComponent(
            routeSelected,
            onRouteSelected = { routeSelected = it },
            Modifier.fillMaxWidth(),
        )
    }
}