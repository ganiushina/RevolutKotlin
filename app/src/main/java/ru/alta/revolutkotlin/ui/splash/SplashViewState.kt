package ru.alta.revolutkotlin.ui.splash

import ru.alta.revolutkotlin.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null) : BaseViewState<Boolean?>(authenticated, error)