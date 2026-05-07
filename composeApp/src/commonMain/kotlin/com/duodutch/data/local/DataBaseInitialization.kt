package com.duodutch.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteDriver

// Agora o construtor exige que a plataforma diga qual driver usar
fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>,
    driver: SQLiteDriver
): AppDatabase {
    return builder
        .setDriver(driver) // Usa o driver injetado
        .build()
}