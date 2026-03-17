package com.duodutch.utils

import kotlin.math.abs

fun Double.toCurrencyString(): String {
    val intPart = this.toLong()
    // Pega os centavos e garante que não fiquem negativos
    val fractionalPart = abs(((this - intPart) * 100).toInt())
    // Garante que o centavo sempre tenha 2 casas (ex: "05" em vez de "5")
    val fractionalStr = fractionalPart.toString().padStart(2, '0')

    return "$intPart,$fractionalStr"
}