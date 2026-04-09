package com.duodutch.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duodutch.domain.models.Transaction
import com.duodutch.domain.repositories.TransactionRepository
import com.duodutch.domain.usecases.CalculateJointBalanceUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// A "Bandeja" de dados da Home
data class HomeUiState(
    val transactions: List<Transaction> = emptyList(),
    val totalBalance: Double = 0.0
)

class HomeViewModel(
    private val transactionRepository: TransactionRepository,
    private val calculateJointBalanceUseCase: CalculateJointBalanceUseCase
) : ViewModel() {

    // O PADRÃO OURO: Transformamos o fluxo do banco no estado da UI
    // Usamos stateIn para que o fluxo sobreviva a rotações de tela e economize bateria
    val uiState: StateFlow<HomeUiState> = transactionRepository.getTransactionsStream("casal_123")
        .map { listaDeTransacoes ->
            HomeUiState(
                transactions = listaDeTransacoes,
                totalBalance = calculateJointBalanceUseCase(listaDeTransacoes)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )
}