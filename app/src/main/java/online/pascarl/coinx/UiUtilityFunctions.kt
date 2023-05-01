package online.pascarl.coinx

import java.text.NumberFormat
import java.util.*

fun formatCurrency(symbol:String, value: Double): String {
    val formatter = NumberFormat.getCurrencyInstance()
    val currency = Currency.getInstance(symbol)
    formatter.currency = currency
    formatter.maximumFractionDigits = currency.defaultFractionDigits
    val formatted = formatter.format(value)
    return formatted.replace(currency.symbol, "${currency.symbol} ")
}

fun getCurrentTime(): String{
    val currentTime = Calendar.getInstance().time.hours
    val salutation = if(currentTime in 0..12){
        "Good morning"
    }
    else if (currentTime in 13..15){
        "Good afternoon"

    }
    else{
        "Good evening"

    }
    return salutation
}