package ru.alta.revolutkotlin.data.provider

import kotlinx.coroutines.channels.ReceiveChannel
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.entity.User
import ru.alta.revolutkotlin.model.CurrenciesResult


interface RemoteDataProvider {
    fun subscribeToAllCurrencies(): ReceiveChannel<CurrenciesResult>
    suspend fun getCurrencyByName(name: String): Currency?
    suspend fun saveCurrency(currency: Currency): Currency
    suspend fun getCurrentUser(): User?
    suspend fun deleteCurrency(name: String)
}