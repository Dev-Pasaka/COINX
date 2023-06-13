package online.pascarl.coinx.screens.auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.UpdatePassword
import online.pascarl.coinx.model.UpdatePasswordResponse

class UpdatePasswordViewModel: ViewModel() {
    var newPassword by mutableStateOf("")
    var confirmNewPassword by mutableStateOf("")

    /** Interaction */
    private var _showPassword by mutableStateOf(false)
    val showPassword get() = _showPassword

    private var _showConfirmNewPassword by mutableStateOf(false)
    val showConfirmNewPassword get() = _showConfirmNewPassword

    fun hideNewPassword(){
        _showPassword = !_showPassword
    }
    fun hideConfirmNewPassword(){
        _showConfirmNewPassword = !_showConfirmNewPassword
    }




    /** Form Submission */
    var formValidationPassed by mutableStateOf(false)
    private var _isPasswordUpdateSuccessful by mutableStateOf(false)
    val isPasswordUpdateSuccessful get() = _isPasswordUpdateSuccessful
    private fun formValidation(){
        formValidationPassed =  (newPassword != confirmNewPassword || newPassword.length < 6)
    }
    suspend fun updatePassword(){
        formValidation()
        try {
            val response = KtorClient.httpClient.post<UpdatePasswordResponse>("https://coinx.herokuapp.com/updatePassword"){
                contentType(ContentType.Application.Json)
                body = UpdatePassword(
                    newPassword = newPassword,
                    phoneNumber = PHONENUMBER
                )
            }
            _isPasswordUpdateSuccessful = response.status
            println(response)

        }catch(_:Exception){
            _isPasswordUpdateSuccessful = false
        }
    }
}