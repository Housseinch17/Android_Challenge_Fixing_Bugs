package com.tedmob.challenge.applicationtofix.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.theme.AppTheme

@Composable
fun HomePage(
    onRedirectToProfile: () -> Unit,
) {
    val viewModel = viewModel<HomeViewModel>()
    val username by viewModel.username.collectAsState()

    HomeUI(
        username,
        onRedirectToProfile = onRedirectToProfile,
        Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                )
            )
            .fillMaxSize(),
    )
}


@Composable
private fun HomeUI(
    username: String?,
    onRedirectToProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (username != null) {
            Text("Welcome, $username!")
        } else {
            Text("Welcome!")
        }
        TextButton(onClick = onRedirectToProfile) {
            Text("Go to profile")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeUI_Preview() {
    AppTheme {
        HomeUI(
            "test",
            onRedirectToProfile = {},
            Modifier.fillMaxSize(),
        )
    }
}