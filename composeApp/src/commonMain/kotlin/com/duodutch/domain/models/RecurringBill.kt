package com.duodutch.domain.models

data class RecurringBill(
    val id: String,
    val name: String,
    val amount: Double,
    val dueDayOfMonth: Int
)