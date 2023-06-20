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
    var formatedPhoneNumber by mutableStateOf("+(254) ")
    val phoneNumber get() = formatedPhoneNumber.replace("[()\\s]".toRegex(), "")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")



    private var _showPassword by mutableStateOf(false)
    val showPassword get() = _showPassword
    private var _showConfirmPassword by mutableStateOf(false)
    val showConfirmPassword get() = _showConfirmPassword
    private var _showCircularProgressBar by mutableStateOf(false)
    val showCircularProcessBar get() = _showCircularProgressBar

    var registerScreenIndex by mutableStateOf(0)
        private set

    fun next(){
        registerScreenIndex++
    }
    fun back(){
        registerScreenIndex--
    }
    fun hidePassword(){
        _showPassword = !_showPassword
    }
    fun hideConfirmPassword(){
        _showConfirmPassword = !_showConfirmPassword
    }



    /** Create Account */
    suspend fun createAccount(): String? {
        _showCircularProgressBar = true
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

