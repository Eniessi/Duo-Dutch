package com.duodutch

import androidx.compose.ui.window.ComposeUIViewController
import com.duodutch.data.local.getRoomDatabase // Importante!

fun MainViewController() = ComposeUIViewController {
    // 1. O iOS descobre a pasta segura no iPhone
    val builder = getDatabaseBuilder()
    // 2. Criamos a Base de Dados
    val database = getRoomDatabase(builder)

    // 3. Entregamos a base de dados à aplicação!
    App(database = database)
}