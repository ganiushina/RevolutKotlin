package ru.alta.revolutkotlin.ui.currency

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_currency.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.model.CurrencyViewModel
import ru.alta.revolutkotlin.ui.base.BaseActivity
import u.alta.revolutkotlin.common.getColorInt
import java.text.SimpleDateFormat
import java.util.*

class CurrencyActivity : BaseActivity<CurrencyViewState.Data, CurrencyViewState>() {

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
    override val model: CurrencyViewModel by viewModel()
    private var currency: Currency? = null
    var color = Currency.Color.WHITE



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
            model.loadCurrency(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_currency_title)
            initView()
        }
    }

    override fun renderData(data: CurrencyViewState.Data) {
        if (data.isDeleted) finish()
        this.currency = data.currency
        initView()
    }

    fun initView() {
        currency?.let { currency ->
            removeEditListener()
            et_title.setText(currency.title)
            et_body.setText(currency.text)
            toolbar.setBackgroundColor(currency.color.getColorInt(this))
            supportActionBar?.title = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(currency.lastChanged)
        } ?: let {
            supportActionBar?.title =   getString(R.string.new_currency_title)
        }
        setEditListener()

        colorPicker.onColorClickListener = {
            toolbar.setBackgroundColor(color.getColorInt(this))
            color = it
            saveCurrency()
        }

        }

    private fun removeEditListener(){
        et_title.removeTextChangedListener(textChahgeListener)
        et_body.removeTextChangedListener(textChahgeListener)
    }

    private fun setEditListener(){
        et_title.removeTextChangedListener(textChahgeListener)
        et_body.removeTextChangedListener(textChahgeListener)
    }


    fun saveCurrency() {
        if (et_title.text == null || et_title.text!!.length < 3) return
        currency = currency?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date(),
            color = color
        ) ?: Currency(
            et_title.text.toString(),
            et_body.text.toString(),
            color
        )
        currency?.let {
            model.save(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = menuInflater.inflate(R.menu.currency, menu).let { true }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteCurrency().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun deleteCurrency() {
        alert {
            messageResource = R.string.currency_delete_message
            negativeButton(R.string.currency_delete_cancel) { dialog -> dialog.dismiss() }
            positiveButton(R.string.currency_delete_ok) { model.deleteCurrency() }
        }.show()
    }



}