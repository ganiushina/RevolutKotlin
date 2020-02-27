package ru.alta.revolutkotlin.ui.main

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrenciesResult
import ru.alta.revolutkotlin.ui.base.BaseViewModel

class MainViewModel(private val currenciesRepository: CurrenciesRepository) : BaseViewModel<List<Currency>?, MainViewState>() {

    private val currenciesObserver = object : Observer<CurrenciesResult> {
        override fun onChanged(t: CurrenciesResult?) {
            t ?: return

            when(t){
                is CurrenciesResult.Success<*> -> {
                    viewStateLiveData.value =
                        MainViewState(currencies = t.data as? List<Currency>)
                }
                is CurrenciesResult.Error -> {
                    viewStateLiveData.value =
                        MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryCurrency = currenciesRepository.getCurrencies()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryCurrency.observeForever(currenciesObserver)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    @VisibleForTesting
    public override fun onCleared() {
        repositoryCurrency.removeObserver(currenciesObserver)
        super.onCleared()
    }

}