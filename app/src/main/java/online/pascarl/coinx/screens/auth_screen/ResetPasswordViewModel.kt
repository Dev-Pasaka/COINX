package online.pascarl.coinx.screens.auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ResetPasswordViewModel: ViewModel() {
    var email by mutableStateOf("")
    private var _isPasswordResetSuccessful by mutableStateOf(false)
    val isPasswordResetSuccessful get() = _isPasswordResetSuccessful

    suspend fun resetPassword(){
        _isPasswordResetSuccessful = if (email.isNotEmpty()){
            try{
                val auth = Firebase.auth
                auth.sendPasswordResetEmail(email).await()
                true
            } catch (e: Exception){
                false
            }
        }else{
            false
        }

    }
}