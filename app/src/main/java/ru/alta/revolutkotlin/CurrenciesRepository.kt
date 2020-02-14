package ru.alta.revolutkotlin

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.provider.FireStoreProvider
import ru.alta.revolutkotlin.data.provider.RemoteDataProvider

object CurrenciesRepository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getCurrencies() = remoteProvider.subscribeToAllCurrencies()
    fun saveCurrency(currency: Currency) = remoteProvider.saveCurrency(currency)
    fun getCurrencyByName(name: String) = remoteProvider.getCurrencyByName(name)

}