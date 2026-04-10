package com.duodutch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.duodutch.data.local.AppDatabase
import com.duodutch.di.AppContainer

import com.duodutch.theme.DuoDutchTheme
import com.duodutch.ui.features.main.MainScreen

@Composable
fun App(
    database: AppDatabase? = null
) {
    // Só criamos a fábrica se o banco de dados já tiver sido injetado pelas plataformas nativas
    val appContainer = remember(database) {
        if (database != null) AppContainer(database) else null
    }

    DuoDutchTheme {
        if (appContainer != null) {
            // O container desce para a MainScreen
            MainScreen(appContainer = appContainer)
        }
        // Nota: Numa app real, se o appContainer for null, mostraríamos um ecrã de Splash/Loading.
    }
}