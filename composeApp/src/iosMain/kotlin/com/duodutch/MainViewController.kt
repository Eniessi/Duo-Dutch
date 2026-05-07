package com.duodutch

import androidx.compose.ui.window.ComposeUIViewController
import androidx.sqlite.driver.bundled.BundledSQLiteDriver // <-- O Driver Embutido
import com.duodutch.data.local.getRoomDatabase

fun MainViewController() = ComposeUIViewController {
    val builder = getDatabaseBuilder()

    // Injetamos o motor multiplataforma no iOS
    val database = getRoomDatabase(builder, BundledSQLiteDriver())

    App(database = database)
}