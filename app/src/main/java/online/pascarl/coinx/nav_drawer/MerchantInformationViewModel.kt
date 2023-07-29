package online.pascarl.coinx.nav_drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.PaymentMethods

class MerchantInformationViewModel:ViewModel() {
    var selectedPaymentMethod by mutableStateOf("Mpesa Safaricom")
        private set
    var mpesaSafaricomNumberInput by mutableStateOf("07 ")
    val mpesaSafaricomNumber get() = mpesaSafaricomNumberInput.replace("\\s+".toRegex(), "")

    val paymentMethods = listOf<String>("Mpesa Safaricom", "Mpesa Paybill", "Mpesa Till")
    fun changePaymentMethod(paymentMethod: String){
        when(paymentMethod){
            "Mpesa Safaricom" -> selectedPaymentMethod = "Mpesa Safaricom"
            "Mpesa Paybill" -> selectedPaymentMethod = "Mpesa Paybill"
            "Mpesa Till" -> selectedPaymentMethod = "Mpesa Till"
        }

    }
}