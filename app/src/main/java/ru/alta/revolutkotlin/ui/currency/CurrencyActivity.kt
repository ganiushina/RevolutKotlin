package ru.alta.revolutkotlin.ui.currency

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.entity.Currency.Color.*
import ru.alta.revolutkotlin.model.CurrencyViewModel
import ru.alta.revolutkotlin.ui.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.*

class CurrencyActivity : BaseActivity<Currency?, CurrencyViewState>() {
    companion object {
        private val EXTRA_CURRENCY = CurrencyActivity::class.java.name + "extra.Currency"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

        fun start(context: Context, currencyName: String? = null) {
            val intent = Intent(context, CurrencyActivity::class.java)
            intent.putExtra(EXTRA_CURRENCY, currencyName)
            context.startActivity(intent)
        }
    }

    override val layoutRes = R.layout.activity_currency
    override val viewModel: CurrencyViewModel by lazy { ViewModelProvider(this).get(CurrencyViewModel::class.java) }
    private var currency: Currency? = null



    val textChahgeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            saveCurrency()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val currencyName = intent.getStringExtra(EXTRA_CURRENCY)

        currencyName?.let {
            viewModel.loadCurrency(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_currency_title)
        }
    }

    override fun renderData(data: Currency?) {
        this.currency = data
        supportActionBar?.title = this.currency?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(currency!!.lastChanged)
        } ?: getString(R.string.new_currency_title)

        initView()
    }

    fun initView() {
        currency?.let { currency ->
            et_title.setText(currency.title)
            et_body.setText(currency.text)
            val color = when (currency.color) {
                WHITE -> R.color.white
                YELLOW -> R.color.yellow
                GREEN -> R.color.green
                BLUE -> R.color.blue
                RED -> R.color.red
                VIOLET -> R.color.violet
                PINK -> R.color.pink
            }

            toolbar.setBackgroundColor(ContextCompat.getColor(this, color))
        }

        et_title.addTextChangedListener(textChahgeListener)
        et_body.addTextChangedListener(textChahgeListener)
    }

    fun saveCurrency() {
        if (et_title.text == null || et_title.text!!.length < 3) return
        currency = currency?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Currency(
            et_title.text.toString(),
            et_body.text.toString()
        )
        currency?.let {
            viewModel.save(it)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }



}