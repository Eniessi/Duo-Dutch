package com.duodutch.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recurring_bills")
data class RecurringBillEntity(
    @PrimaryKey
    val id: String,

    val householdId: String, // Isolamento de dados do casal
    val createdByUserId: String, // Quem cadastrou a conta?

    val name: String,
    val amount: Double,
    val dueDayOfMonth: Int // O dia do mês em que a conta vence (ex: 15)
)