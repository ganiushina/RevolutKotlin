package ru.alta.revolutkotlin.model

sealed class CurrenciesResult {
    data class Success<out T>(val data: T): CurrenciesResult()
    data class Error(val error: Throwable) : CurrenciesResult()
}