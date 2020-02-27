package ru.alta.revolutkotlin.ui.currency

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewState

class CurrencyViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<CurrencyViewState.Data>(data, error){
    data class Data(val isDeleted: Boolean = false, val currency: Currency? = null)
}