package com.duodutch.data.local

import androidx.room.ConstructedBy // NOVO
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor // NOVO
import com.duodutch.data.local.dao.RecurringBillDao
import com.duodutch.data.local.dao.TransactionDao
import com.duodutch.data.local.entities.RecurringBillEntity
import com.duodutch.data.local.entities.TransactionEntity

@Database(
    // ATUALIZADO: Adicionámos a RecurringBillEntity à lista de entidades
    entities = [TransactionEntity::class, RecurringBillEntity::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

    // NOVO: A porta de acesso ao nosso novo DAO
    abstract fun recurringBillDao(): RecurringBillDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
