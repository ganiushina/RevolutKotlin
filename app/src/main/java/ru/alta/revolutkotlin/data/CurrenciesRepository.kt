package ru.alta.revolutkotlin.data

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.provider.RemoteDataProvider

class CurrenciesRepository(val remoteProvider: RemoteDataProvider) {

    fun getCurrencies() = remoteProvider.subscribeToAllCurrencies()
    fun saveCurrency(currency: Currency) = remoteProvider.saveCurrency(currency)
    fun getCurrencyByName(name: String) = remoteProvider.getCurrencyByName(name)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteCurrency(name: String) = remoteProvider.deleteCurrency(name)

}