package com.example.cellular.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Teal40,
    secondary = Purple40,
    tertiary = UsageDataColor,
    background = Color(0xFFF8F9FA),
    surface = SurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1B1B1B),
    onSurface = Color(0xFF1B1B1B)
)

private val DarkColors = darkColorScheme(
    primary = Teal80,
    secondary = Purple80,
    tertiary = UsageDataColor,
    background = SurfaceDark,
    surface = Color(0xFF2A2A2A),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun CellularTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

object CellularThemeExt {
    val usageDataColor: Color
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme.tertiary
    val usageMinutesColor: Color
        @Composable
        @ReadOnlyComposable
        get() = UsageMinutesColor
    val usageSmsColor: Color
        @Composable
        @ReadOnlyComposable
        get() = UsageSmsColor
}
