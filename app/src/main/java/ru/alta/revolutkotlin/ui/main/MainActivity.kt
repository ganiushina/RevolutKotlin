package ru.alta.revolutkotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.android.viewModel
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.di.appModule
import ru.alta.revolutkotlin.di.currencyModule
import ru.alta.revolutkotlin.di.mainModule
import ru.alta.revolutkotlin.di.splashModule
import ru.alta.revolutkotlin.ui.base.BaseActivity
import ru.alta.revolutkotlin.ui.currency.CurrencyActivity
import ru.alta.revolutkotlin.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Currency>?>() {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

    override val model: MainViewModel by viewModel()

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

    override fun onCreateOptionsMenu(menu: Menu?) =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> showLogoutDialog()?.let { true }
        else -> false
    }

    fun showLogoutDialog() {
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_ok) { onLogout() }
            negativeButton(R.string.logout_dialog_cancel) { dialog -> dialog.dismiss() }
        }.show()
    }

    fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }

    override fun renderData(data: List<Currency>?) {
        data?.let {
            adapter.currencies = it
        }
    }
}
