package ru.alta.revolutkotlin.ui.splash

import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.data.errors.NoAuthException

class SplashViewModel (private val currenciesRepository: CurrenciesRepository) : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        currenciesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}