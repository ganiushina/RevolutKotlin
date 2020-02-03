package ru.alta.revolutkotlin.data.entity

import java.util.*

object CurrenciesRepository {

    private val currencies: List<Currency>

    init {
        currencies = listOf(
            Currency(
                "AUD",
                "100",
                0xfff06292.toInt()
            ),
            Currency(
                "BGN",
                "200",
                0xff9575cd.toInt()
            ),
            Currency(
                "BRL",
                "300",
                0xff64b5f6.toInt()
            ),
            Currency(
                "CAD",
                "400",
                0xff4db6ac.toInt()
            ),
            Currency(
                "CHF",
                "500",
                0xffb2ff59.toInt()
            ),
            Currency(
                "HRK",
                "600",
                0xffffeb3b.toInt()
            ),
            Currency(
                "JPY",
                "700",
                0xfff06292.toInt()
            ),
            Currency(
                "ILS",
                "800",
                0xff9575cd.toInt()
            ),
            Currency(
                "KRW",
                "900",
                0xff64b5f6.toInt()
            ),
            Currency(
                "MYR",
                "1000",
                0xff4db6ac.toInt()
            ),
            Currency(
                "AUD",
                "100",
                0xfff06292.toInt()
            ),
            Currency(
                "BGN",
                "200",
                0xff9575cd.toInt()
            ),
            Currency(
                "BRL",
                "300",
                0xff64b5f6.toInt()
            ),
            Currency(
                "CAD",
                "400",
                0xff4db6ac.toInt()
            ),
            Currency(
                "CHF",
                "500",
                0xffb2ff59.toInt()
            ),
            Currency(
                "HRK",
                "600",
                0xffffeb3b.toInt()
            ),
            Currency(
                "JPY",
                "700",
                0xfff06292.toInt()
            ),
            Currency(
                "ILS",
                "800",
                0xff9575cd.toInt()
            ),
            Currency(
                "KRW",
                "900",
                0xff64b5f6.toInt()
            ),
            Currency(
                "MYR",
                "1000",
                0xff4db6ac.toInt()
            )
        )
    }

    fun getCurrencies(): List<Currency>{
        return currencies
    }


}