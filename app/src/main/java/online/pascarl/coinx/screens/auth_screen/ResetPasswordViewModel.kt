package online.pascarl.coinx.screens.auth_screen

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.Phone
import online.pascarl.coinx.model.SendOtpResponse

class ResetPasswordViewModel: ViewModel() {
    var phoneNumber by mutableStateOf("")
    private var _otpCode:String? by mutableStateOf(null)
    val otpCode get() = _otpCode
    private var _phoneInputError:Boolean by mutableStateOf(false)
    val phoneInputError get() =  _phoneInputError
    suspend fun sendOtp(){
        try {
            val response = KtorClient.httpClient.post<SendOtpResponse>("https://coinx.herokuapp.com/sendOtp"){
                contentType(ContentType.Application.Json)
                body = Phone(phoneNumber = phoneNumber)
            }
            println(response)
            _otpCode = response.otpCode
            OTPRESPONSE = response.otpCode
        }catch(_:Exception){
            _otpCode = null
        }
        validatePhoneInput()
    }
    private fun validatePhoneInput(){
        _phoneInputError = otpCode == null
    }
}

var OTPRESPONSE:String?  = null
