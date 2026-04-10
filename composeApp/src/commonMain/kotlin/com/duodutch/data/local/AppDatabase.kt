package com.duodutch.data.local

import androidx.room.ConstructedBy // NOVO
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor // NOVO
import com.duodutch.data.local.dao.TransactionDao
import com.duodutch.data.local.entities.TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class) // A MÁGICA QUE O COMPILADOR EXIGIU
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}

// Criamos uma "promessa" de construtor. O motor KSP vai gerar a implementação
// real ('actual') para Android e iOS por baixo dos panos!
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}