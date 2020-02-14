package ru.alta.revolutkotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.adapter.CurrenciesRvAdapter
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.ui.currency.CurrencyActivity
import ru.alta.revolutkotlin.ui.base.BaseActivity
import ru.alta.revolutkotlin.ui.splash.SplashActivity

class MainActivity : BaseActivity<List<Currency>?, MainViewState>(), LogoutDialog.LogoutListener {

    companion object {
        fun start(context: Context) = Intent(context, MainActivity::class.java).apply {
            context.startActivity(this)
        }
    }

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
        listOf<String>().forEach {
            if(it.isEmpty()){
                return@forEach
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?) =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> showLogoutDialog()?.let { true }
        else -> false
    }

    fun showLogoutDialog() {
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
        LogoutDialog.createInstance().show(supportFragmentManager, LogoutDialog.TAG)
    }

    override fun onLogout() {
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
