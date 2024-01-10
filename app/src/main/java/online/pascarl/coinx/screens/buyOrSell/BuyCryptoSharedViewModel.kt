package online.pascarl.coinx.screens.buyOrSell

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.OrderData
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetBuyAds
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetSellAds
import java.text.NumberFormat
import java.util.Currency

class BuyCryptoSharedViewModel: ViewModel() {
    var buyAdData: GetBuyAds? by mutableStateOf(null)
    var sellAdData: GetSellAds? by mutableStateOf(null)
    var youWillPay by mutableDoubleStateOf(0.0)
        private set
    var youWillGet by mutableDoubleStateOf(0.0)
        private set

    var youSold by mutableDoubleStateOf(0.0)
        private set
    var youWillReceive by  mutableDoubleStateOf(0.0)
        private set


    var orderData = mutableStateOf(OrderData())
    fun formatCurrency(symbol: String = "KES", amount:Double = youWillPay): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")
    }

    fun updateCryptoAmountAndFiatAmount(fiatAmount:Double, cryptoAmount:Double){
        youWillPay = fiatAmount
        youWillGet = cryptoAmount
    }

    fun updateSellCryptoAmountAndFiatAmount(fiatAmount: Double, cryptoAmount: Double){
        youSold = cryptoAmount
        youWillReceive = fiatAmount

    }

    fun copyToClipboard(context: Context, text: String, message:String = "Phone Number Copied") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)

        // Show a toast or provide feedback that text is copied
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}