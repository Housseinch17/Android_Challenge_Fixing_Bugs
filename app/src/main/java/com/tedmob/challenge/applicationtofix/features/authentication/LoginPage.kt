package com.tedmob.challenge.applicationtofix.features.authentication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.ui.AppProgress
import com.tedmob.challenge.applicationtofix.ui.AppTopBar
import com.tedmob.challenge.applicationtofix.ui.PasswordToggle

@Composable
fun LoginPage(
    onRedirectToMain: () -> Unit,
    onRedirectToRegister: () -> Unit,
) {
    val viewModel = viewModel<LoginViewModel>()
    val loginState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        Modifier.fillMaxSize(),
    ) {
        AppTopBar(
            title = { Text("Login") },
            Modifier.fillMaxWidth(),
        )

        LoginUI(
            modifier = Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                    )
                )
                .weight(1f)
                .fillMaxWidth(),
            onLogin = viewModel::login,
            onRegister = onRedirectToRegister,
            username = loginState.username,
            updateUsername = { newUsername ->
                viewModel.updateUsername(newUsername)
            },
            password = loginState.password,
            updatePassword = { newPassword ->
                viewModel.updatePassword(
                    password = newPassword
                )
            },
        )
    }
    loginState.missingError?.let {
        LoginMissingDialog(
            onDismiss = {
                viewModel.dismissMissingError(missingError = null)
            },
            text = {
                Text(it)
            },
        )
    }

    if (loginState.isLoggingIn) {
        AppProgress()
    }
    loginState.loginError?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::consumeLoginState,
            confirmButton = {
                TextButton(onClick = viewModel::consumeLoginState) {
                    Text("Close")
                }
            },
            text = {
                Text(error)
            },
        )
    }
    if (loginState.redirectToMain) {
        onRedirectToMain()
        viewModel.consumeLoginState()
    }
}


@Composable
private fun LoginUI(
    modifier: Modifier = Modifier,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    username: String,
    updateUsername: (String) -> Unit,
    password: String,
    updatePassword: (String) -> Unit,
) {
    var isPasswordMasked: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier
            .padding(16.dp),
    ) {
        TextField(
            username,
            onValueChange = {
                updateUsername(it)
            },
            Modifier.fillMaxWidth(),
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            password,
            onValueChange = { updatePassword(it) },
            Modifier.fillMaxWidth(),
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            trailingIcon = {
                PasswordToggle(
                    isPasswordMasked,
                    onToggle = { isPasswordMasked = !isPasswordMasked },
                )
            },
            visualTransformation = if (isPasswordMasked)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
        )

        Spacer(Modifier.weight(1f))
        Button(
            onClick = onLogin,
            Modifier.fillMaxWidth(),
        ) {
            Text("Login")
        }
        OutlinedButton(
            onClick = {
                onRegister()
            },
            Modifier.fillMaxWidth(),
        ) {
            Text("Register")
        }
    }
}


@Composable
private fun LoginMissingDialog(
    onDismiss: () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        modifier,
        text = text,
    )
}


@Preview(showBackground = true)
@Composable
private fun LoginUI_Preview() {
    AppTheme {
        LoginUI(
            modifier = Modifier.fillMaxSize(),
            onLogin = {},
            onRegister = {},
            username = "User",
            updateUsername = {},
            password = "123456",
            updatePassword = {},
        )
    }
}