package com.duodutch.data.repositories

import com.duodutch.data.local.dao.TransactionDao
import com.duodutch.data.local.entities.TransactionEntity
import com.duodutch.domain.models.Transaction
import com.duodutch.domain.models.TransactionType
import com.duodutch.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val dao: TransactionDao
) : TransactionRepository {

    // O Flow emite listas continuamente. Quando o Room mudar, esta função emite uma nova lista.
    override fun getTransactionsStream(householdId: String): Flow<List<Transaction>> {
        return dao.getTransactionsByHousehold(householdId).map { entities ->
            entities.map { entity ->
                Transaction(
                    id = entity.id,
                    title = entity.title,
                    date = entity.date,
                    amount = entity.amount,
                    type = TransactionType.valueOf(entity.type)
                )
            }
        }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        val entity = TransactionEntity(
            id = transaction.id,
            householdId = "casal_123",
            createdByUserId = "user_456",
            title = transaction.title,
            date = transaction.date,
            amount = transaction.amount,
            type = transaction.type.name
        )
        dao.insertTransaction(entity)
    }

    override suspend fun deleteTransaction(id: String) {
        // Agora a ponte é direta e cirúrgica
        dao.deleteTransactionById(id)
    }
}