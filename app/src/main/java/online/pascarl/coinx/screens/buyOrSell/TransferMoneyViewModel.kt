package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TransferMoneyViewModel:ViewModel() {
    var paymentMethodType by mutableStateOf("")
        private set
    var businessName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set
    fun paymentMethod(paymentMethod:String){
        when(paymentMethod){
            "M-Pesa Safaricom" ->{
                paymentMethodType = paymentMethod
                businessName = paymentMethod
                phoneNumber = ""

            }
            "M-Pesa Paybill" -> {}
            "Equity Bank" -> {}
            "Absa Bank" -> {}
            "KCB Bank" -> {}
        }
    }
}