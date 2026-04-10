package com.duodutch.domain.usecases

import com.duodutch.domain.models.RecurringBill
import kotlinx.datetime.Clock // O motor oficial da versão 0.6.1
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class GetDaysUntilDueUseCase {
    operator fun invoke(bill: RecurringBill): Int {
        val now = Clock.System.now()
        val today = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val currentDay = today.dayOfMonth

        return if (bill.dueDayOfMonth >= currentDay) {
            bill.dueDayOfMonth - currentDay
        } else {
            (30 - currentDay) + bill.dueDayOfMonth
        }
    }
}