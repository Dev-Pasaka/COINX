package online.pascarl.coinx.screens.auth_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.tasks.await
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.SignIn
import online.pascarl.coinx.model.Token
import online.pascarl.coinx.roomDB.RoomUser


class SignInViewModel: ViewModel() {


    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var backendAuthToken:String? by mutableStateOf("")

    var roomUser:RoomUser? by mutableStateOf(RoomUser(email = "", token = ""))

    private var _circularProgressBar by mutableStateOf(false)
    val circularProgressBar get() = _circularProgressBar
    private var result:Token? by mutableStateOf(Token(""))
    fun isSignInSuccessful(): Boolean {
        val response = result
        return if (response != null) {
            backendAuthToken = response.token
            println(backendAuthToken)
            true
        } else false
    }

    fun circularProgressBar(){
        _circularProgressBar = true
    }



    suspend fun getSignInToken(){
        try {
            val response = KtorClient.httpClient.post<Token>("https://coinx.herokuapp.com/signIn"){
                contentType(ContentType.Application.Json)
                body = SignIn(
                    email = email,
                    password = password
                )
            }

            result = response
        }catch (_: Exception){
            result = null
        }
        _circularProgressBar = false

    }


}