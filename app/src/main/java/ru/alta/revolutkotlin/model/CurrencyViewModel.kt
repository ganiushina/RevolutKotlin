package ru.alta.revolutkotlin.model

import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.ui.currency.CurrencyViewState

class CurrencyViewModel(private val currenciesRepository: CurrenciesRepository) : BaseViewModel<CurrencyViewState.Data, CurrencyViewState>() {

    private val pendingCurrency: Currency?
        get() = viewStateLiveData.value?.data?.currency

    fun save(currency: Currency){
        viewStateLiveData.value = CurrencyViewState(CurrencyViewState.Data(currency = currency))
    }

    fun loadCurrency(name: String) {
        currenciesRepository.getCurrencyByName(name).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when(result){
                    is CurrenciesResult.Success<*> -> CurrencyViewState(CurrencyViewState.Data(currency = result.data as Currency))
                    is CurrenciesResult.Error -> CurrencyViewState(error = result.error)
                }
            }
        }
    }
    fun deleteCurrency() {
        pendingCurrency?.let {
            currenciesRepository.deleteCurrency(it.title).observeForever { result ->
                viewStateLiveData.value = when (result) {
                    is CurrenciesResult.Success<*> -> CurrencyViewState(CurrencyViewState.Data(isDeleted = true))
                    is CurrenciesResult.Error -> CurrencyViewState(error = result.error)
                }
            }
        }
    }

    override fun onCleared(){
        pendingCurrency?.let {
            currenciesRepository.saveCurrency(it)
        }
    }

}