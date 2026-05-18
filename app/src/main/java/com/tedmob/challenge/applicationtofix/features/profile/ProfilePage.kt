package com.tedmob.challenge.applicationtofix.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.data.entity.User
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.ui.AppTopBar

@Composable
fun ProfilePage(
    onSettings: () -> Unit,
) {
    val viewModel = viewModel<ProfileViewModel>()
    val user by viewModel.user.collectAsState()

    Column(
        Modifier.fillMaxSize(),
    ) {
        AppTopBar(
            title = { Text("Profile") },
            actions = {
                IconButton(onClick = onSettings) {
                    Icon(Icons.Default.Settings, "Settings")
                }
            }
        )
        ProfileUI(
            user,
            Modifier
                .weight(1f)
                .fillMaxWidth(),
        )
    }
}


@Composable
private fun ProfileUI(
    user: User?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (user == null) {
            Text("You are not logged in.")
        } else {
            Text("First Name: ${user.firstName}")
            Spacer(Modifier.height(8.dp))
            Text("Last Name: ${user.lastName}")
            Spacer(Modifier.height(8.dp))
            Text("Username: ${user.username}")
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileUI_Preview() {
    AppTheme {
        ProfileUI(
            User(
                "First Name",
                "Last Name",
                "test",
            ),
            Modifier.fillMaxSize(),
        )
    }
}