package ru.alta.revolutkotlin.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.alta.revolutkotlin.MainViewState
import ru.alta.revolutkotlin.data.entity.CurrenciesRepository

class MainViewModel() : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(CurrenciesRepository.getCurrencies())
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData

    override fun onCleared() {
        super.onCleared()
        println("onCleared")
    }

}