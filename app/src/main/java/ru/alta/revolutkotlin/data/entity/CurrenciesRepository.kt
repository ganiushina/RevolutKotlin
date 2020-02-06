package ru.alta.revolutkotlin.data.entity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CurrenciesRepository {

    private val currenciesLiveData = MutableLiveData<List<Currency>>()

    private val curencies: MutableList<Currency> = mutableListOf(

            Currency(
                "AUD",
                "100",
                Currency.Color.WHITE
            ),
            Currency(
                "BGN",
                "200",
                Currency.Color.YELLOW
            ),
            Currency(
                "BRL",
                "300",
                Currency.Color.GREEN
            ),
            Currency(
                "CAD",
                "400",
                Currency.Color.BLUE
            ),
            Currency(
                "CHF",
                "500",
                Currency.Color.VIOLET
            ),
            Currency(
                "HRK",
                "600",
                Currency.Color.PINK
            ),
            Currency(
                "JPY",
                "700",
                Currency.Color.WHITE
            ),
            Currency(
                "ILS",
                "800",
                Currency.Color.PINK
            ),
            Currency(
                "KRW",
                "900",
                Currency.Color.VIOLET
            ),
            Currency(
                "MYR",
                "1000",
                Currency.Color.BLUE
            ),
            Currency(
                "AUD",
                "100",
                Currency.Color.GREEN
            ),
            Currency(
                "BGN",
                "200",
                Currency.Color.YELLOW
            )
        )

    init {
        currenciesLiveData.value = curencies
    }

    fun saveCurrency(currency: Currency){
        addOrReplace(currency)
        currenciesLiveData.value = curencies
    }

    private fun addOrReplace(currency: Currency){
        val currencyTmp : Currency
        for(i in curencies.indices){
            if(curencies[i] == currency){
                curencies[i] = currency
                currencyTmp = currency
                curencies.remove(curencies[i])
                curencies.add(0,currencyTmp)
                return
            }
        }

        curencies.add(0,currency)
    }


    fun getCurrencies(): LiveData<List<Currency>> {
        return currenciesLiveData
    }


}