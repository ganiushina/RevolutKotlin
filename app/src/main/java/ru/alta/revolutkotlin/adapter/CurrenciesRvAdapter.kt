package ru.alta.revolutkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_currency.view.*
import ru.alta.revolutkotlin.R
import ru.alta.revolutkotlin.data.entity.Currency

class CurrenciesRvAdapter(val onItemViewClick : ((currency: Currency) -> Unit)? = null) : RecyclerView.Adapter<CurrenciesRvAdapter.ViewHolder>() {

    var currencies : List<Currency> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        )

    override fun getItemCount() = currencies.size

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) = vh.bind(currencies[pos])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currency: Currency) = with(itemView) {
            tv_title.text = currency.title
            tv_text.text = currency.text
            val color = when(currency.color){
                Currency.Color.WHITE -> R.color.white
                Currency.Color.YELLOW -> R.color.yellow
                Currency.Color.GREEN -> R.color.green
                Currency.Color.BLUE -> R.color.blue
                Currency.Color.RED -> R.color.red
                Currency.Color.VIOLET -> R.color.violet
                Currency.Color.PINK -> R.color.pink
            }

            (this as CardView).setCardBackgroundColor(ContextCompat.getColor(itemView.context, color))

            setOnClickListener {
                onItemViewClick?.invoke(currency)
            }

        }
    }
}