package ru.alta.revolutkotlin.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module
import ru.alta.revolutkotlin.data.CurrenciesRepository
import ru.alta.revolutkotlin.data.provider.FireStoreProvider
import ru.alta.revolutkotlin.data.provider.RemoteDataProvider
import ru.alta.revolutkotlin.model.CurrencyViewModel
import ru.alta.revolutkotlin.ui.main.MainViewModel
import ru.alta.revolutkotlin.ui.splash.SplashViewModel


val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { CurrenciesRepository(get()) }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val currencyModule = module {
    viewModel { CurrencyViewModel(get()) }
}
