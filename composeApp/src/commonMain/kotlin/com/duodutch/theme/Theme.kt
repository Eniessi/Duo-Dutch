package com.duodutch.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    primaryContainer = OrangeDarkContainer,
    background = BackgroundDark,
    surface = SurfaceDark,
    outline = OutlineDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark,
    error = RedExpense
)

@Composable
fun DuoDutchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}