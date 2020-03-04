package ru.alta.revolutkotlin

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.alta.revolutkotlin.di.appModule
import ru.alta.revolutkotlin.di.currencyModule
import ru.alta.revolutkotlin.di.mainModule
import ru.alta.revolutkotlin.di.splashModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, mainModule, currencyModule))
    }
}