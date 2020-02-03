package ru.alta.revolutkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.model.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: CurrenciesRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = CurrenciesRvAdapter()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let {
                adapter.currencies = it.currencies
            }
        })
    }
}
