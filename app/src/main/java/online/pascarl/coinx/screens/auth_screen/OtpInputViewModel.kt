package online.pascarl.coinx.screens.auth_screen

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel

class OtpInputViewModel: ViewModel() {
    var otp by mutableStateOf("")
    private var _isOtpVerified by mutableStateOf(false)
    val isOtpVerified get() = _isOtpVerified
    var otpResponse:String? by mutableStateOf(null)
    fun verifyOtp(){

        _isOtpVerified = otp == OTPRESPONSE

    }

}