package com.duodutch.ui.features.recurring

import com.duodutch.domain.models.RecurringBill

import androidx.lifecycle.ViewModel
import com.duodutch.domain.usecases.GetDaysUntilDueUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus
import kotlinx.datetime.minus
import kotlin.math.abs

data class RecurringUiState(
    val currentDayNumber: String = "",
    val weekDaysInitials: List<String> = emptyList(),
    val weekDaysNumbers: List<String> = emptyList(),
    val todayIndex: Int = 3,
    val upcomingBillsWithDaysLeft: List<Pair<RecurringBill, Int>> = emptyList()
)

class RecurringViewModel : ViewModel() {

    private val getDaysUntilDueUseCase = GetDaysUntilDueUseCase()

    private val _uiState = MutableStateFlow(RecurringUiState())
    val uiState: StateFlow<RecurringUiState> = _uiState.asStateFlow()

    init {
        loadCalendarAndBills()
    }

    private fun loadCalendarAndBills() {
        // 1. Lógica do Calendário que estava na UI vem para cá
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val weekDates = (-3..3).map { offset ->
            if (offset < 0) {
                today.minus(abs(offset), DateTimeUnit.DAY)
            } else if (offset > 0) {
                today.plus(offset, DateTimeUnit.DAY)
            } else {
                today
            }
        }

        val initials = weekDates.map { it.dayOfWeek.name.first().toString() }
        val numbers = weekDates.map { it.day.toString() }
        val todayNumber = today.day.toString()

        // 2. Lógica das Contas
        val mockBills = listOf(
            RecurringBill("1", "Spotify Duo", 27.90, 15),
            RecurringBill("2", "Electricity", 145.50, 20),
            RecurringBill("3", "Fiber Internet", 99.90, 25),
            RecurringBill("4", "Apartment Rent", 1500.00, 5)
        )

        // O ViewModel aciona o UseCase e emparelha a conta com os dias restantes
        val billsWithDays = mockBills.map { bill ->
            Pair(bill, getDaysUntilDueUseCase(bill))
        }

        // 3. Atualizamos o estado da tela
        _uiState.update { currentState ->
            currentState.copy(
                currentDayNumber = todayNumber,
                weekDaysInitials = initials,
                weekDaysNumbers = numbers,
                todayIndex = 3, // Na lista de -3 a 3, o hoje sempre será o índice 3
                upcomingBillsWithDaysLeft = billsWithDays
            )
        }
    }
}