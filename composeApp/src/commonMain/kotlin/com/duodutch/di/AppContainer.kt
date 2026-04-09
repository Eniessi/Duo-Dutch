package com.duodutch.di

import com.duodutch.data.local.AppDatabase
import com.duodutch.data.repositories.TransactionRepositoryImpl
import com.duodutch.domain.repositories.TransactionRepository
import com.duodutch.domain.usecases.CalculateJointBalanceUseCase
import com.duodutch.domain.usecases.GetDaysUntilDueUseCase

// O Container recebe o Banco de Dados bruto que vem do iOS ou do Android
class AppContainer(private val database: AppDatabase) {

    // 1. DAOs (Privados, pois ninguém fora do container deve falar com eles diretamente)
    private val transactionDao = database.transactionDao()

    // 2. Repositories (A Ponte)
    // Usamos 'by lazy' para que o objeto só seja criado na memória RAM
    // no momento exato em que algum ecrã precisar dele.
    val transactionRepository: TransactionRepository by lazy {
        TransactionRepositoryImpl(transactionDao)
    }

    // 3. Use Cases (O Domínio)
    val getDaysUntilDueUseCase: GetDaysUntilDueUseCase by lazy {
        GetDaysUntilDueUseCase()
    }

    val calculateJointBalanceUseCase: CalculateJointBalanceUseCase by lazy {
        CalculateJointBalanceUseCase()
    }
}