package online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model.CreateBuyOrder
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model.CreateBuyOrderResponse
import online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model.CreateSellOrderDto
import online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model.CreateSellOrderResponse
import online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model.SellOrderStatus
import java.text.NumberFormat
import java.util.Currency

class SellOrderConfirmationViewModel(
    val application: Application,
    val userRepository: UserRepository
) : ViewModel() {
    val client = KtorClient.httpClient
    var orderStatus by mutableStateOf(
        SellOrderStatus(orderCreated = false, orderIsCreating = false)
    )

    val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    val authToken = roomDB.getUser(id = "12345678")

    fun formatCurrency(symbol: String = "KES", amount: Double): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

    suspend fun createSellOrder(sellOrderDto: CreateSellOrderDto) = withContext(context = Dispatchers.Main.immediate) {
            orderStatus = orderStatus.copy(orderIsCreating = true)
            val createSellAdResponse = try {

                client.post<CreateSellOrderResponse> {
                    url("${AppConfigs.COINX_API}cryptoSellOrder")
                    headers {
                        if (authToken != null) {
                            append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                        }
                    }
                    contentType(ContentType.Application.Json)
                    body = sellOrderDto
                }

            } catch (e: Exception) {
                Log.e("NetworkError", "${e.printStackTrace()}")
                null
            }

            println("Order status $createSellAdResponse")

            if (createSellAdResponse != null) {
                orderStatus = orderStatus.copy(orderCreated = true, orderIsCreating = false)
            } else {
                orderStatus = orderStatus.copy(orderCreated = false, orderIsCreating = true)

            }
        }



}