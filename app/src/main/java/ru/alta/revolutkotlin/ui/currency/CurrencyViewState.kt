package ru.alta.revolutkotlin.ui.currency

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewState

class CurrencyViewState(currency: Currency? = null, error: Throwable? = null) : BaseViewState<Currency?>(currency, error)