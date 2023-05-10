package online.pascarl.coinx

import io.ktor.client.request.*
import online.pascarl.coinx.apis.KtorClient
import online.pascarl.coinx.model.Cryptocurrency



suspend fun request(): List<Cryptocurrency> {
    return KtorClient.httpClient.get("http://10.0.2.2:8080/cryptoPrices")
}


