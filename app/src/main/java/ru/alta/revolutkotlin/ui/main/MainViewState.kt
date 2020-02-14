package ru.alta.revolutkotlin.ui.main

import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.base.BaseViewState


class MainViewState(val currencies: List<Currency>? = null, error: Throwable? = null): BaseViewState<List<Currency>?>(currencies, error)