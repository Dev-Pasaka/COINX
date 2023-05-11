package online.pascarl.coinx.networkcalls

import io.ktor.client.request.*
import online.pascarl.coinx.apis.KtorClient
import online.pascarl.coinx.model.Cryptocurrency



suspend fun getCryptoPrices(): List<Cryptocurrency> {
    return KtorClient.httpClient.get("https://coinx.herokuapp.com/cryptoPrices")
}


