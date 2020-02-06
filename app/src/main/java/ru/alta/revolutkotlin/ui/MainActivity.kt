package ru.alta.revolutkotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.model.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: CurrenciesRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rv_currency.layoutManager = GridLayoutManager(this, 2)
        adapter = CurrenciesRvAdapter { currency ->
            CurrencyActivity.start(this, currency)
        }
        rv_currency.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let {
                adapter.currencies = it.currencies
            }
        })

        fab.setOnClickListener {
            CurrencyActivity.start(this)
        }
    }
}
