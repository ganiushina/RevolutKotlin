package ru.alta.revolutkotlin.ui.splash

import kotlinx.coroutines.launch
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.data.errors.NoAuthException

class SplashViewModel (private val currenciesRepository: CurrenciesRepository) : BaseViewModel<Boolean?>() {

    fun requestUser() {
        launch {
        currenciesRepository.getCurrentUser().let {
            setData(true)
        } ?: setError(NoAuthException())
        }
    }
}