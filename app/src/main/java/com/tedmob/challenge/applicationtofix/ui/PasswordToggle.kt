package com.tedmob.challenge.applicationtofix.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PasswordToggle(
    isMasked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(onClick = onToggle, modifier) {
        Icon(
            if (isMasked) Icons.Default.Visibility else Icons.Default.VisibilityOff,
            "Toggle Password Visibility",
        )
    }
}