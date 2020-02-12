package ru.alta.revolutkotlin.data.provider

import androidx.lifecycle.LiveData
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrenciesResult


interface RemoteDataProvider {
    fun subscribeToAllCurrencies(): LiveData<CurrenciesResult>
    fun getCurrencyByName(name: String): LiveData<CurrenciesResult>
    fun saveCurrency(currency: Currency): LiveData<CurrenciesResult>
}