package online.pascarl.coinx.screens.auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.tasks.await
import online.pascarl.coinx.Country
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.getCountriesList
import online.pascarl.coinx.model.AccountCreationResponse
import online.pascarl.coinx.model.User

class CreateAccountViewModel : ViewModel() {
    var fullName by mutableStateOf("")
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")



    private var _showPassword by mutableStateOf(false)
    val showPassword get() = _showPassword
    private var _showConfirmPassword by mutableStateOf(false)
    val showConfirmPassword get() = _showConfirmPassword
    private var _showCircularProgressBar by mutableStateOf(false)
    val showCircularProcessBar get() = _showCircularProgressBar

    fun hidePassword(){
        _showPassword = !_showPassword
    }
    fun hideConfirmPassword(){
        _showConfirmPassword = !_showConfirmPassword
    }

    /** FormValidation */
    private var _isFullNameValid by mutableStateOf(false)
    val isFullNameValid get() =  _isFullNameValid
    private var _isUsernameValid by mutableStateOf(false)
    val isUsernameValid get() = _isUsernameValid
    private var _isEmailValid by mutableStateOf(false)
    val isEmailValid get() = _isEmailValid
    private var _isPasswordValid by mutableStateOf(false)
    val isPasswordValid get() = _isPasswordValid
    private var _isPhoneValid by mutableStateOf(false)
    val isPhoneValid get() = _isPhoneValid

    private var _formValidationPassed by mutableStateOf(false)

    fun formValidation(){
        _isFullNameValid = fullName.length < 6
        _isUsernameValid = username.length < 3
        _isEmailValid = !email.contains("@")
        _isPasswordValid = (password != confirmPassword || password.length < 6)
        _isPhoneValid = !phoneNumber.contains("+254")

        _formValidationPassed = !(_isPasswordValid || isUsernameValid || isEmailValid || isFullNameValid || _isPhoneValid)


    }

    /** Create Account */
    suspend fun createAccount(): String? {
        _showCircularProgressBar = true
        if (_formValidationPassed){
            return try {
                val registerUser = registerUser()
                println("$registerUser")
                if (validateAccountCreationResponse(response = registerUser) == true){
                    return  "user created"
                }
                else if(validateAccountCreationResponse(response = registerUser) == false) {
                    _showCircularProgressBar = false
                    return  "user exists"
                }else return null
            }
            catch (_: Exception) {
                _showCircularProgressBar = false
                null
            }
        }else{
            _showCircularProgressBar = false
            return null
        }
    }

    private fun validateAccountCreationResponse(response: AccountCreationResponse?):Boolean?{
        return response?.isRegistered
    }

    private suspend fun registerUser(): AccountCreationResponse?{
       return try {
            KtorClient.httpClient.post<AccountCreationResponse>("https://coinx.herokuapp.com/registerUser"){
                contentType(ContentType.Application.Json)
                body = User(
                    fullName = fullName,
                    username = username,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password
                )
            }
        }catch (e: Exception){
            null
        }
    }
}
