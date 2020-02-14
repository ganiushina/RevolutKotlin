package ru.alta.revolutkotlin.model

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.alta.revolutkotlin.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.ui.currency.CurrencyViewState
import ru.alta.revolutkotlin.ui.main.MainViewState


class CurrencyViewModel : BaseViewModel<Currency?, CurrencyViewState>() {

    init {
        viewStateLiveData.value = CurrencyViewState()
    }

    private var pendingCurrency: Currency? = null

    fun save(currency: Currency){
        pendingCurrency = currency
    }

    fun loadCurrency(name: String) {
        CurrenciesRepository.getCurrencyByName(name).observeForever(object : Observer<CurrenciesResult> {
            override fun onChanged(t: CurrenciesResult?) {
                t ?: return
                when (t) {
                    is CurrenciesResult.Success<*> -> {
                        viewStateLiveData.value = CurrencyViewState(currency = t.data as Currency)
                    }
                    is CurrenciesResult.Error -> {
                        viewStateLiveData.value = CurrencyViewState(error = t.error)
                    }
                }
            }
        })
    }

    override fun onCleared(){
        pendingCurrency?.let {
            CurrenciesRepository.saveCurrency(it)
        }
    }

}