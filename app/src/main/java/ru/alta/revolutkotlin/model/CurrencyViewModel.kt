package ru.alta.revolutkotlin.model

import androidx.lifecycle.ViewModel
import ru.alta.revolutkotlin.data.entity.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency


class CurrencyViewModel: ViewModel() {
    private var pendingCurrency: Currency? = null

    fun save(currency: Currency){
        pendingCurrency = currency
    }

    override fun onCleared(){
        pendingCurrency?.let {
            CurrenciesRepository.saveCurrency(it)
        }
    }

}