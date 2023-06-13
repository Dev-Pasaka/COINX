package online.pascarl.coinx.screens.auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.Phone
import online.pascarl.coinx.model.SendOtpResponse

class OtpInputViewModel: ViewModel() {
    var otp by mutableStateOf("")
    private var _isOtpVerified by mutableStateOf(false)
    val isOtpVerified get() = _isOtpVerified
    private var _otpResponse:Boolean by mutableStateOf(false)
    val otpResponse get() =  _otpResponse
    var seconds by mutableStateOf(59)
        private set

    private var timerJob: Job? = null


    suspend fun reSendOtp(){

    }

        private fun startTimer() {
        timerJob = viewModelScope.launch {
            if (seconds < 0){
                seconds = 59
                while (seconds >= 0) {
                    delay(1000)
                    seconds--
                }
            }else{
                while (seconds >= 0) {
                    delay(1000)
                    seconds--
                }
            }
        }
    }

    init {
        startTimer()
    }

}