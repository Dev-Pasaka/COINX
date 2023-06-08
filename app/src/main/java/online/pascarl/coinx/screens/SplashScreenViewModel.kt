package online.pascarl.coinx.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.roomDB.RoomUser

class SplashScreenViewModel: ViewModel() {

    var roomUser by mutableStateOf(RoomUser())
    private  var _isUserSignedIn by mutableStateOf(false)
    val isUserSignedIn get() = _isUserSignedIn

    suspend fun getUserData() {
        val result = try {
            KtorClient.httpClient.get<UserData>{
                url("https://coinx.herokuapp.com/getUserData?email=${roomUser.email}")
                headers {
                    append(HttpHeaders.Authorization, "Bearer ${roomUser.token}")
                }
            }
        }catch (_: Exception){
            println("An exception was called")
            null
        }
        println("Here is user information $result")
        _isUserSignedIn = result != null
    }

}