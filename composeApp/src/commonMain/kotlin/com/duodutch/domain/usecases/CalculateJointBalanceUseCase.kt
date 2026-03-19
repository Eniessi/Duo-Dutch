package com.duodutch.domain.usecases

import com.duodutch.domain.models.Transaction
import com.duodutch.domain.models.TransactionType

class CalculateJointBalanceUseCase {
    operator fun invoke(transactions: List<Transaction>): Double {
        var balance = 0.0

        for (transaction in transactions) {
            when (transaction.type) {
                TransactionType.INCOME -> balance += transaction.amount
                TransactionType.EXPENSE -> balance -= transaction.amount
            }
        }

        return balance
    }
}