package com.duodutch.domain.repositories

import com.duodutch.domain.models.Transaction

interface TransactionRepository {
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(id: String)
}