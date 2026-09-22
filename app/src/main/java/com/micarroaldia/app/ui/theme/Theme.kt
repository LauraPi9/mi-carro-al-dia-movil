package com.micarroaldia.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = SurfaceWhite,
    secondary = AccentBlue,
    onSecondary = SurfaceWhite,
    error = ErrorRed,
    background = BackgroundLight,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    outline = OutlineGray
)

private val DarkColors = darkColorScheme(
    primary = AccentBlue,
    onPrimary = SurfaceWhite,
    secondary = AccentBlue,
    error = ErrorRed,
    background = NavyPrimaryDark,
    onBackground = SurfaceWhite,
    surface = NavyPrimary,
    onSurface = SurfaceWhite
)

@Composable
fun MiCarroAlDiaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
