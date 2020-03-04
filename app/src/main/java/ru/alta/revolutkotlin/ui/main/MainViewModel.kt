package ru.alta.revolutkotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrenciesResult
import ru.alta.revolutkotlin.ui.base.BaseViewModel

class MainViewModel(currenciesRepository: CurrenciesRepository) : BaseViewModel<List<Currency>?>() {

    private val currencyChannel = currenciesRepository.getCurrencies()

    init {
        launch {
            currencyChannel.consumeEach {
                when(it){
                    is CurrenciesResult.Success<*> -> setData(it.data as? List<Currency>)
                    is CurrenciesResult.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared() {
        currencyChannel.cancel()
        super.onCleared()
    }

}