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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tedmob.challenge.applicationtofix.theme.AppTheme
import com.tedmob.challenge.applicationtofix.ui.AppProgress
import com.tedmob.challenge.applicationtofix.ui.AppTopBar
import com.tedmob.challenge.applicationtofix.ui.AppTopBarBack
import com.tedmob.challenge.applicationtofix.ui.PasswordToggle

@Composable
fun RegisterPage(
    onRedirectToLogin: () -> Unit,
) {
    val viewModel = viewModel<RegisterViewModel>()
    val registerState by viewModel.state.collectAsState()
    var missingError: String? by remember { mutableStateOf(null) }

    Column(
        Modifier.fillMaxSize(),
    ) {
        AppTopBar(
            title = { Text("Register") },
            Modifier.fillMaxWidth(),
            navigationIcon = {
                AppTopBarBack()
            }
        )

        RegisterUI(
            onRegister = { firstName, lastName, username, password, confirmPassword ->
                if (firstName.isEmpty()) {
                    missingError = "First Name is required"
                } else if (lastName.isEmpty()) {
                    missingError = "Last Name is required"
                } else if (username.isEmpty()) {
                    missingError = "Username is required"
                } else if (password.isEmpty()) {
                    missingError = "Password is required"
                } else {
                    viewModel.register(
                        firstName,
                        lastName,
                        username,
                        password,
                    )
                }
            },
            Modifier
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                    )
                )
                .weight(1f)
                .fillMaxWidth(),
        )
    }

    missingError?.let {
        RegisterMissingDialog(
            onDismiss = { missingError = null },
            text = {
                Text(it)
            },
        )
    }

    if (registerState.isRegistering) {
        AppProgress()
    }
    registerState.registerError?.let { error ->
        AlertDialog(
            onDismissRequest = viewModel::consumeRegisterState,
            confirmButton = {
                TextButton(onClick = viewModel::consumeRegisterState) {
                    Text("Close")
                }
            },
            text = {
                Text(error)
            },
        )
    }
    if (registerState.redirectToLogin) {
        onRedirectToLogin()
        viewModel.consumeRegisterState()
    }
}


@Composable
private fun RegisterUI(
    onRegister: (
        firstName: String,
        lastName: String,
        username: String,
        pass: String,
        confirmPass: String,
    ) -> Unit,
    modifier: Modifier = Modifier,
) {
    var firstName: String by remember { mutableStateOf("") }
    var lastName: String by remember { mutableStateOf("") }
    var username: String by remember { mutableStateOf("") }
    var pass: String by remember { mutableStateOf("") }
    var confirmPass: String by remember { mutableStateOf("") }

    var isPasswordMasked: Boolean by remember { mutableStateOf(true) }
    var isConfirmPasswordMasked: Boolean by remember { mutableStateOf(true) }

    Column(
        modifier
            .padding(16.dp),
    ) {
        TextField(
            firstName,
            onValueChange = { firstName = it },
            Modifier.fillMaxWidth(),
            label = { Text("First Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            lastName,
            onValueChange = { lastName = it },
            Modifier.fillMaxWidth(),
            label = { Text("Last Name") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            username,
            onValueChange = { username = it },
            Modifier.fillMaxWidth(),
            label = { Text("Username") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            pass,
            onValueChange = { pass = it },
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
        Spacer(Modifier.height(16.dp))
        TextField(
            confirmPass,
            onValueChange = { confirmPass = it },
            Modifier.fillMaxWidth(),
            label = { Text("Confirm Password") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            trailingIcon = {
                PasswordToggle(
                    isConfirmPasswordMasked,
                    onToggle = { isConfirmPasswordMasked = !isConfirmPasswordMasked },
                )
            },
            visualTransformation = if (isPasswordMasked)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
        )

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                onRegister(firstName, lastName, username, pass, confirmPass)
            },
            Modifier.fillMaxWidth(),
        ) {
            Text("Register")
        }
    }
}


@Composable
private fun RegisterMissingDialog(
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
private fun RegisterUI_Preview() {
    AppTheme {
        RegisterUI(
            onRegister = { _, _, _, _, _ -> },
            Modifier.fillMaxSize(),
        )
    }
}