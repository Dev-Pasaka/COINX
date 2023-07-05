package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.OrderData
import java.text.NumberFormat
import java.util.Currency

class BuyOrSellSharedViewModel: ViewModel() {
    var youWillPay = mutableStateOf(0.0)
    var youWillGet = mutableStateOf(0.0)
    var youSold = mutableStateOf(0.0)
    var youWillReceive = mutableStateOf(0.0)


    val orderData = mutableStateOf(OrderData())
    fun formatCurrency(symbol: String = "KES", amount:Double = youWillPay.value): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")
    }
}