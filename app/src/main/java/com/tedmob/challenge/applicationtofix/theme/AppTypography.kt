package com.tedmob.challenge.applicationtofix.theme

import androidx.compose.material3.Typography


val AppTypography = Typography().run {
    copy(
        displayLarge = displayLarge,/*.copy()*/
        displayMedium = displayMedium,/*.copy()*/
        displaySmall = displaySmall,/*.copy()*/
        headlineLarge = headlineLarge,/*.copy()*/
        headlineMedium = headlineMedium,/*.copy()*/
        headlineSmall = headlineSmall,/*.copy()*/
        titleLarge = titleLarge,/*.copy()*/
        titleMedium = titleMedium,/*.copy()*/
        titleSmall = titleSmall,/*.copy()*/
        bodyLarge = bodyLarge,/*.copy()*/
        bodyMedium = bodyMedium,/*.copy()*/
        bodySmall = bodySmall,/*.copy()*/
        labelLarge = labelLarge,/*.copy()*/
        labelMedium = labelMedium,/*.copy()*/
        labelSmall = labelSmall,/*.copy()*/
    )
}

val AppTextStyle = AppTypography.bodyLarge