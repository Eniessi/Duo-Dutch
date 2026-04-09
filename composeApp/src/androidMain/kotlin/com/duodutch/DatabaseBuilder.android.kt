package com.duodutch

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duodutch.data.local.AppDatabase

// O Android precisa de um "Context" para descobrir onde fica a pasta privada do aplicativo
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext

    // Pega o caminho físico seguro dentro do Android para criar o ficheiro .db
    val dbFile = appContext.getDatabasePath("duodutch.db")

    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}