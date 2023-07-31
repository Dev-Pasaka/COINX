package online.pascarl.coinx.nav_drawer

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import online.pasaka.model.user.payment_platforms.MpesaPaybill
import online.pasaka.model.user.payment_platforms.MpesaSafaricom
import online.pasaka.model.user.payment_platforms.MpesaTill
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.AccountCreationResponse
import online.pascarl.coinx.model.User
import online.pascarl.coinx.model.become_merchant.BecomeMerchantResponse
import online.pascarl.coinx.model.become_merchant.PaymentMethod
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository

class MerchantPaymentInformationViewModel(val application: Application,val userRepository: UserRepository):ViewModel() {
    var selectedPaymentMethod by mutableStateOf("Mpesa Safaricom")
        private set
    var mpesaSafaricomNumberInput by mutableStateOf("07 ")
    val mpesaSafaricomNumber:String? get() = mpesaSafaricomNumberInput.replace("\\s+".toRegex(), "")
    var bussinessNumber:String? by mutableStateOf("")
    var accountNumber:String? by mutableStateOf("")
    var tillNumber:String? by mutableStateOf("")

    var isBecomeMerchantSuccess  by mutableStateOf(false)
        private set

    val paymentMethods = listOf<String>("Mpesa Safaricom", "Mpesa Paybill", "Mpesa Till")
    fun changePaymentMethod(paymentMethod: String){
        when(paymentMethod){
            "Mpesa Safaricom" -> selectedPaymentMethod = "Mpesa Safaricom"
            "Mpesa Paybill" -> selectedPaymentMethod = "Mpesa Paybill"
            "Mpesa Till" -> selectedPaymentMethod = "Mpesa Till"
        }

    }

    suspend fun addMpesaSafaricom(){
        isBecomeMerchantSuccess = false
        val roomDB = RoomViewModel(
            application = application,
            userRepository = userRepository
            )
        val authToken = roomDB.getUser(id = "12345678")
        isBecomeMerchantSuccess = try {
            KtorClient.httpClient.post<BecomeMerchantResponse>("https://coinx-2590f763d976.herokuapp.com/becomeMerchant") {
                headers {
                    if (authToken != null) {
                        append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                    }
                }
                contentType(ContentType.Application.Json)
                body = PaymentMethod(
                    mpesaSafaricom = MpesaSafaricom(phone = mpesaSafaricomNumber),

                )

            }.status
        }catch (e:Exception){
            false
        }
    }
    suspend fun addMpesaTill(){
        isBecomeMerchantSuccess = false
        val roomDB = RoomViewModel(
            application = application,
            userRepository = userRepository
        )
        val authToken = roomDB.getUser(id = "12345678")
        isBecomeMerchantSuccess = try {
            KtorClient.httpClient.post<BecomeMerchantResponse>("https://coinx-2590f763d976.herokuapp.com/becomeMerchant") {
                headers {
                    if (authToken != null) {
                        append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                    }
                }
                contentType(ContentType.Application.Json)
                body = PaymentMethod(
                    mpesaTill = MpesaTill(tillNumber = tillNumber)
                )

            }.status
        }catch (e:Exception){
            false
        }
    }
    suspend fun addMpesaPaybill(){
        isBecomeMerchantSuccess = false
        val roomDB = RoomViewModel(
            application = application,
            userRepository = userRepository
        )
        val authToken = roomDB.getUser(id = "12345678")
        isBecomeMerchantSuccess = try {
            KtorClient.httpClient.post<BecomeMerchantResponse>("https://coinx-2590f763d976.herokuapp.com/becomeMerchant") {
                headers {
                    if (authToken != null) {
                        append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                    }
                }
                contentType(ContentType.Application.Json)
                body = PaymentMethod(
                    mpesaPaybill = MpesaPaybill(
                        bussinessNumber = bussinessNumber,
                        accountNumber = accountNumber
                    )
                )

            }.status
        }catch (e:Exception){
            false
        }
    }
}