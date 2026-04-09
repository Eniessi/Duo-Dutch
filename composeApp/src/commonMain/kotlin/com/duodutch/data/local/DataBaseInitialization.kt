package com.duodutch.data.local

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

// Esta função recebe um "Builder" (construído de forma diferente no Android e no iOS)
// e adiciona o motor SQLite multiplataforma antes de compilar a base de dados final.
fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        // O BundledSQLiteDriver garante que a versão do SQL é exatamente a mesma
        // no Android e no iOS, evitando bugs bizarros de inconsistência.
        .setDriver(BundledSQLiteDriver())
        .build()
}