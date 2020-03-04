package ru.alta.revolutkotlin.model

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.launch
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.ui.currency.CurrencyData

class CurrencyViewModel(private val currenciesRepository: CurrenciesRepository) : BaseViewModel<CurrencyData>() {

    private val pendingCurrency: Currency?
        get() = getViewState().poll()?.currency

    fun save(currency: Currency){
        setData(CurrencyData(currency = currency))
    }

    fun loadCurrency(name: String) {
        launch {
            try {
                currenciesRepository.getCurrencyByName(name).let {
                    setData(CurrencyData(currency = it))
                }

            } catch (e: Throwable) {
                setError(e)
            }
        }
    }
    fun deleteCurrency() {
        pendingCurrency?.let { currency ->
            launch {
                try {
                    currenciesRepository.deleteCurrency(currency.title)
                    setData(CurrencyData(isDeleted = true))
                } catch (e: Throwable) {
                    setError(e)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingCurrency?.let {
                try {
                    currenciesRepository.saveCurrency(it)
                } catch (e: Throwable) {
                    setError(e)
                }
            }
            super.onCleared()

        }
    }

}