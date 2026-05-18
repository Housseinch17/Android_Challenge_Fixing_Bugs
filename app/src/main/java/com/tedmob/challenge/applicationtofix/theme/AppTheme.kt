package com.tedmob.challenge.applicationtofix.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppLightColorScheme,
        shapes = AppShapes,
        typography = AppTypography,
    ) {
        ProvideTextStyle(AppTextStyle) {
            content()
        }
    }
}