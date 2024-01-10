package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.model.SignIn
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model.CreateBuyOrder
import online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model.CreateBuyOrderResponse
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model.OrderStatus
import java.text.NumberFormat
import java.util.Currency

class BuyConfirmationScreenViewModel(
    val application: Application,
    val userRepository: UserRepository
) : ViewModel() {


    val client = KtorClient.httpClient


    var orderStatus by mutableStateOf(OrderStatus(orderCreated = false, orderIsCreating = false))


    var buyOrder: CreateBuyOrder? by mutableStateOf(null)

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

    suspend fun createBuyOrder() {
        coroutineScope {
            orderStatus = orderStatus.copy(orderIsCreating = true)
            val createButAdResponse = try {
                if (buyOrder != null) {
                    client.post<CreateBuyOrderResponse> {
                        url("${AppConfigs.COINX_API}cryptoBuyOrder")
                        headers {
                            if (authToken != null) {
                                append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                            }
                        }
                        contentType(ContentType.Application.Json)
                        body = buyOrder as CreateBuyOrder
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                Log.e("NetworkError", "${e.printStackTrace()}")
                null
            }

            println("Order status $createButAdResponse")

            if (createButAdResponse != null) {
                orderStatus = orderStatus.copy(orderCreated = true, orderIsCreating = false)
            } else {
                orderStatus = orderStatus.copy(orderCreated = false,orderIsCreating = true)

            }
        }
    }

}