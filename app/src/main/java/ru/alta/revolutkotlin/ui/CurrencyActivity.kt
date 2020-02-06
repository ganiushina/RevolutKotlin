package ru.alta.revolutkotlin.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.model.CurrencyViewModel
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.data.entity.Currency.Color.*
import java.text.SimpleDateFormat
import java.util.*

class CurrencyActivity : AppCompatActivity() {
    companion object {
        private val EXTRA_NOTE = CurrencyActivity::class.java.name + "extra.Currency"
        private const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 2000L

        fun start(context: Context, currency: Currency? = null) {
            val intent = Intent(context, CurrencyActivity::class.java)
            intent.putExtra(EXTRA_NOTE, currency)
            context.startActivity(intent)
        }
    }

    private var currency: Currency? = null
    lateinit var viewModel: CurrencyViewModel

    val textChahgeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveCurrency()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        currency = intent.getParcelableExtra(EXTRA_NOTE)
        setSupportActionBar(toolbar)
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)

        supportActionBar?.title = currency?.let {
            SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(it.lastChanged)
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

        Handler().postDelayed({
            currency = currency?.copy(
                title = et_title.text.toString(),
                text = et_body.text.toString(),
                lastChanged = Date()
            ) ?: createNewCurrency()

            currency?.let { viewModel.save(it) }

        }, SAVE_DELAY)
    }

    private fun createNewCurrency(): Currency = Currency(et_title.text.toString(), et_body.text.toString())

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}