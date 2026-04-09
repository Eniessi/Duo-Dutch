package com.duodutch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// O nome da tabela no SQLite será "transactions"
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,

    // A MÁGICA PARA CASAIS: Estes dois campos separam um app básico de um app Sênior
    val householdId: String,   // A que "Casa/Casal" esta transação pertence?
    val createdByUserId: String, // Quem pagou? (João ou Maria?)

    val title: String,
    val date: String,
    val amount: Double,
    val type: String // No SQL guardamos como String ("INCOME" ou "EXPENSE")
)