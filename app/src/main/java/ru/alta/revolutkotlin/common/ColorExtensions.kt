package u.alta.revolutkotlin.common

import android.content.Context
import androidx.core.content.ContextCompat
import ru.alta.revolutkotlin.data.entity.Currency
import ru.alta.revolutkotlin.R


fun Currency.Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(
        context, when (this) {
            Currency.Color.WHITE -> R.color.white
            Currency.Color.VIOLET -> R.color.violet
            Currency.Color.YELLOW -> R.color.yellow
            Currency.Color.RED -> R.color.red
            Currency.Color.PINK -> R.color.pink
            Currency.Color.GREEN -> R.color.green
            Currency.Color.BLUE -> R.color.blue
        }
    )


fun Currency.Color.getColorRes(): Int = when (this) {
    Currency.Color.WHITE -> R.color.white
    Currency.Color.VIOLET -> R.color.violet
    Currency.Color.YELLOW -> R.color.yellow
    Currency.Color.RED -> R.color.red
    Currency.Color.PINK -> R.color.pink
    Currency.Color.GREEN -> R.color.green
    Currency.Color.BLUE -> R.color.blue
}