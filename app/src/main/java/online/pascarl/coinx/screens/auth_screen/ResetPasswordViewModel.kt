package online.pascarl.coinx.screens.auth_screen

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.Phone
import online.pascarl.coinx.model.VerifyPhoneResponse

import java.util.concurrent.TimeUnit

class ResetPasswordViewModel: ViewModel() {
    var formatedPhoneNumber by mutableStateOf("+(254) ")
    val phoneNumber get() = formatedPhoneNumber.replace("[()\\s]".toRegex(), "")
    var otpCode:String by mutableStateOf("")
    var seconds by mutableStateOf(59)

    private var _isPhoneVerificationSuccessful:Boolean? by mutableStateOf(null)
    val isPhoneVerificationSuccessful get() = _isPhoneVerificationSuccessful

    /**Firebase Authentication Phone*/

    suspend fun verifyPhoneNumber():VerifyPhoneResponse?{
        val result =  try {
           val response = KtorClient.httpClient.post<VerifyPhoneResponse>("https://coinx.herokuapp.com/verifyPhoneNumber"){
                contentType(ContentType.Application.Json)
                body = Phone(phoneNumber = phoneNumber!!)
            }
            if (response.status){
                PHONENUMBER = phoneNumber
                _isPhoneVerificationSuccessful  = true
            }else _isPhoneVerificationSuccessful = false

            response
        }catch(_:Exception){
            null
        }
        println(result)
        return result

    }
    fun sendOtp(activity: Activity, phoneNumber: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)// Replace with your activity reference
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            println("Verification completed ")
        }
        override fun onVerificationFailed(exception: FirebaseException) {
            println("Verification failed ")

        }
        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            println("Code sent")
            VERIFICATIONID = verificationId
            println("Here is your id $VERIFICATIONID")
        }
    }
    suspend fun verifyOtp(): AuthResult? {
        val credential = PhoneAuthProvider.getCredential(VERIFICATIONID, otpCode)
        val result = try {
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .await()
        }catch (_:IllegalArgumentException){
            null
        }
        catch (_:Exception){
            null
        }

        return result

    }

    private var timerJob: Job? = null
     fun startTimer() {
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

}
var VERIFICATIONID = ""
var PHONENUMBER = ""