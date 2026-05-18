package com.tedmob.challenge.applicationtofix.features.settings

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.BuildConfig
import com.tedmob.challenge.applicationtofix.R
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.ui.AppProgress
import com.tedmob.challenge.applicationtofix.ui.AppTopBar
import com.tedmob.challenge.applicationtofix.ui.AppTopBarBack
import kotlinx.coroutines.launch

@Composable
fun SettingsPage(
    onRestartApp: () -> Unit,
) {
    val viewModel = viewModel<SettingsViewModel>()
    val pageState by viewModel.state.collectAsState()

    val uriHandler = LocalUriHandler.current

    Column(
        Modifier.fillMaxSize(),
    ) {
        AppTopBar(
            title = { Text("Settings") },
            Modifier.fillMaxWidth(),
            navigationIcon = { AppTopBarBack() },
        )
        SettingsUI(
            onLogout = { viewModel.logout() },
            onPrivacyPolicy = {
                uriHandler.openUri(uri = "https://www.termsfeed.com/blog/privacy-policy-url/")
            },
            Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                    )
                )
                .fillMaxSize(),
        )
    }

    if (pageState.isLoggingOut) {
        AppProgress()
    } else if (pageState.logOutError != null) {
        AlertDialog(
            onDismissRequest = { viewModel.consumeLogoutState() },
            confirmButton = {
                TextButton(onClick = { viewModel.consumeLogoutState() }) {
                    Text("Close")
                }
            },
            text = { Text(pageState.logOutError.orEmpty()) },
        )
    } else if (pageState.restartApp) {
        LaunchedEffect(Unit) {
            viewModel.consumeLogoutState()
            onRestartApp()
        }
    }
}


@Composable
private fun SettingsUI(
    onLogout: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier,
    ) {
        val clipboard = LocalClipboard.current
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        fun copyVersionToClipboard() {
            coroutineScope.launch {
                clipboard.setClipEntry(
                    ClipEntry(
                        ClipData.newPlainText(
                            "ApplicationToFix version",
                            BuildConfig.VERSION_NAME
                        )
                    )
                )
            }
            Toast.makeText(
                context,
                "\"${BuildConfig.VERSION_NAME}\" copied to clipboard.",
                Toast.LENGTH_LONG
            )
                .show()
        }
        Row(
            Modifier
                .clickable { onPrivacyPolicy() }
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Info, null)
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(R.string.privacy_policy),
                color = Color.Blue
            )
        }

        Row(
            Modifier
                .combinedClickable(
                    onLongClick = { copyVersionToClipboard() },
                    onClick = {},
                )
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Settings, null)
            Spacer(Modifier.width(8.dp))
            Column {
                Text(stringResource(R.string.app_version))
                Text(BuildConfig.VERSION_NAME, style = MaterialTheme.typography.labelMedium)
            }
        }

        Row(
            Modifier
                .clickable { onLogout() }
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.AutoMirrored.Default.Logout, null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.logout))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingsUI_Preview() {
    AppTheme {
        SettingsUI(
            onLogout = {},
            onPrivacyPolicy = {},
            Modifier.fillMaxSize(),
        )
    }
}