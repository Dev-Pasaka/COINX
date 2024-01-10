package online.pascarl.coinx

import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy.GetCryptoBuyOrdersDto

val token =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJDb2lueCIsImlzcyI6IkNvaW54IiwiZW1haWwiOiJkZXYucGFzYWthQGdtYWlsLmNvbSIsImV4cCI6MTcwNTAzODE4NH0.0kT1ok1V61fVTsImfbnu-V2dvmFumb1pyJaL3d2Ane4"


suspend fun getCryptoPrices() {
    val client = KtorClient.httpClient
    val result = client.get<String> {
        url("${AppConfigs.COINX_API}getOrders")
        headers {
            if (token != null) {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
        parameter(key = "orderType", value = "buyOrder")
    }
    val kotlibObj = Json.decodeFromString<GetCryptoBuyOrdersDto>(result)
    println(kotlibObj)
}


suspend fun main() {
    getCryptoPrices()
}