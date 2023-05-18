package online.pascarl.coinx.networkcalls

import io.ktor.client.request.*
import io.ktor.http.*
import online.pascarl.coinx.apis.KtorClient
import online.pascarl.coinx.model.Cryptocurrency
import online.pascarl.coinx.model.Response
import online.pascarl.coinx.model.User
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.model.UserPortfolio


suspend fun getCryptoPrices(): List<Cryptocurrency> {
    return KtorClient.httpClient.get("https://coinx.herokuapp.com/cryptoPrices")
}

suspend fun registerUser(user: User): Response?{
    return try {
        KtorClient.httpClient.post<Response>("https://coinx.herokuapp.com/registerUser"){
             contentType(ContentType.Application.Json)
             body = user
         }

    }catch (e: Exception){
        null
    }
}

suspend fun getUserData(email:String):UserData{
    return KtorClient.httpClient.get(" https://coinx.herokuapp.com/getUserData?email=$email")
}
suspend fun getUserPortfolio(email:String): UserPortfolio {
    return KtorClient.httpClient.get(" https://coinx.herokuapp.com/getUserPortfolio?email=$email")
}
fun validateUserCreationResponse(response: Response?): Boolean?{
   return response?.isRegistered
}


