package com.duodutch.domain.usecases

import com.duodutch.domain.models.RecurringBill
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetDaysUntilDueUseCase {
    operator fun invoke(bill: RecurringBill): Int {
        // Agora o Clock.System vai brilhar verde (resolvido nativamente)
        val now = Clock.System.now()

        val today = now.toLocalDateTime(TimeZone.currentSystemDefault())

        // 2. Atendendo ao compilador: Usando 'day' em vez de 'dayOfMonth'
        val currentDay = today.day

        return if (bill.dueDayOfMonth >= currentDay) {
            bill.dueDayOfMonth - currentDay
        } else {
            (30 - currentDay) + bill.dueDayOfMonth
        }
    }
}