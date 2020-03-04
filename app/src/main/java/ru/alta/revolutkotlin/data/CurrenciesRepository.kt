package ru.alta.revolutkotlin.data

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.provider.RemoteDataProvider

class CurrenciesRepository(val remoteProvider: RemoteDataProvider) {

    fun getCurrencies() = remoteProvider.subscribeToAllCurrencies()
    suspend fun saveCurrency(currency: Currency) = remoteProvider.saveCurrency(currency)
    suspend fun getCurrencyByName(name: String) = remoteProvider.getCurrencyByName(name)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun deleteCurrency(name: String) = remoteProvider.deleteCurrency(name)

}