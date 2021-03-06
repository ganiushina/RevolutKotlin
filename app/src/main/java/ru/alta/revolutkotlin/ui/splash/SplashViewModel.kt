package ru.alta.revolutkotlin.ui.splash

import ru.alta.revolutkotlin.CurrenciesRepository
import ru.alta.revolutkotlin.ui.base.BaseViewModel
import ru.alta.revolutkotlin.data.errors.NoAuthException


class SplashViewModel() : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        CurrenciesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: let {
                SplashViewState(error = NoAuthException())
            }
        }
    }
}