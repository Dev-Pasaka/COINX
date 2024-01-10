package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.util.Currency

class SellAmountViewModel:ViewModel() {

    var cryptoSellAmount by mutableStateOf("")
    val cryptoAmount get() = cryptoSellAmount.toDoubleOrNull()?:0.0



    fun removeLastCharacter(input: String): String {
        return when {
            input.isEmpty() -> input
            input.length == 1 -> ""
            else -> input.substring(0, input.length - 1)
        }
    }

    fun formatCurrency(symbol:String = "KES", amount:Double = cryptoAmount): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val result = formatter.format(amount)
        return result.replace(currency.symbol, "${currency.symbol} ")

    }

}