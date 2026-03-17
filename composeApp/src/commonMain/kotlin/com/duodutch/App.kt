package com.duodutch

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// Importando o nosso ecossistema
import com.duodutch.theme.DuoDutchTheme
import com.duodutch.ui.features.home.HomeScreen
import com.duodutch.ui.features.main.MainScreen

@Composable
@Preview
fun App() {
    DuoDutchTheme {
        MainScreen()
    }
}