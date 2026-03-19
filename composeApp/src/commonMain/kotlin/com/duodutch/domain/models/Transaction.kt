package com.duodutch.domain.models

enum class TransactionType {
    INCOME,
    EXPENSE
}

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Double,
    val type: TransactionType
)