package ru.alta.revolutkotlin.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.currency.CurrencyActivity
import ru.alta.revolutkotlin.ui.base.BaseActivity

class MainActivity : BaseActivity<List<Currency>?, MainViewState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main
    lateinit var adapter: CurrenciesRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv_currency.layoutManager = GridLayoutManager(this, 2)
        adapter = CurrenciesRvAdapter { currency ->
            CurrencyActivity.start(this, currency.title
            )
        }
        rv_currency.adapter = adapter

        fab.setOnClickListener {
            CurrencyActivity.start(this)
        }
    }

    override fun renderData(data: List<Currency>?) {
        data?.let {
            adapter.currencies = it
        }
    }
}
