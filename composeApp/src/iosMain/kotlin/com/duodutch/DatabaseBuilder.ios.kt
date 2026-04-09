package com.duodutch

import androidx.room.Room
import androidx.room.RoomDatabase
import com.duodutch.data.local.AppDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

// No iOS, não existe "Context". Pedimos o caminho diretamente ao sistema de ficheiros da Apple.
@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    // 1. Procuramos a pasta "Documents" da nossa aplicação no iPhone
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    // 2. Montamos o caminho completo do ficheiro
    val dbFilePath = requireNotNull(documentDirectory?.path) + "/duodutch.db"

    // 3. Retornamos o construtor da base de dados
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
        factory = { AppDatabase::class.instantiateImpl() }
    )
}