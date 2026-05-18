package com.tedmob.challenge.applicationtofix.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val AppLightColorScheme = lightColorScheme(
    primary = AppColors.BlueMain,
    onPrimary = Color.White,
    surface = AppColors.GreyBGD,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    outline = AppColors.BlueMain,
)

object AppColors {
    val BlueMain = Color(0x3F, 0x51, 0xB5)
    val GreyBGD = Color(0xEF, 0xF0, 0xF2)
}