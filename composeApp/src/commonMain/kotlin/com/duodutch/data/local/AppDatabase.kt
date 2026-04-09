package com.duodutch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.duodutch.data.local.dao.TransactionDao
import com.duodutch.data.local.entities.TransactionEntity

// O KSP vai ler este @Database e gerar todo o código SQL automaticamente
@Database(
    entities = [TransactionEntity::class],
    version = 1
)
// No KMP, a classe TEM de ser abstract e herdar de RoomDatabase
abstract class AppDatabase : RoomDatabase() {

    // É assim que o resto da aplicação vai aceder ao nosso DAO
    abstract fun transactionDao(): TransactionDao

}