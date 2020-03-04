package ru.alta.revolutkotlin.ui.currency

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewState

data class CurrencyData(val isDeleted: Boolean = false, val currency: Currency? = null)