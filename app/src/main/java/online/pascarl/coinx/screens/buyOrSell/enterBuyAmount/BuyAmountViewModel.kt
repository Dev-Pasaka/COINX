package online.pascarl.coinx.screens.buyOrSell.enterBuyAmount

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetBuyAds
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency

class BuyAmountViewModel:ViewModel(){

    var cryptoBuyAmount by mutableStateOf("")
    var buyAdData:GetBuyAds? by mutableStateOf(null)
    val fiatAmount get() = cryptoBuyAmount.toDoubleOrNull()?:0.0
    val youWillPay  get() = fiatAmount
    val youWillGet get() = DecimalFormat("#.####").format(fiatAmount.div(buyAdData?.cryptoPrice ?: 0.0)).toDouble()
    fun removeLastCharacter(input: String): String {
        return when {
            input.isEmpty() -> input
            input.length == 1 -> ""
            else -> input.substring(0, input.length - 1)
        }
    }
    fun formatCurrency(symbol:String = "KES"): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(fiatAmount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

    // Function to copy text to clipboard
    fun copyToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

        // Show a toast or provide feedback that text is copied
        Toast.makeText(context, "Phone Number Copied", Toast.LENGTH_SHORT).show()
    }

}
