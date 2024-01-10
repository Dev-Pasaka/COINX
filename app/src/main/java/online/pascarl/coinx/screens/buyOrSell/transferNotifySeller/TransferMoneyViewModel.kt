package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.model.BuyOrder
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetBuyAdsDto
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.GetBuyOrderResponse
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.TransferFundsResponse
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.TransferFundsState
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.dto.BuyOrderDto

class TransferMoneyViewModel(val application: Application, val userRepository: UserRepository) :
    ViewModel() {
    val client = KtorClient.httpClient

    var paymentMethodType by mutableStateOf("")
        private set
    var businessName by mutableStateOf("")
        private set
    var phoneNumber by mutableStateOf("")
        private set
    var transferFundsState by mutableStateOf(
        TransferFundsState(
            fundsTrasfered = false,
            fundsTransferring = false
        )
    )
        private set

    fun paymentMethod(paymentMethod: String) {
        when (paymentMethod) {
            "M-Pesa Safaricom" -> {
                paymentMethodType = paymentMethod
                businessName = paymentMethod
                phoneNumber = ""

            }

            "M-Pesa Paybill" -> {}
            "Equity Bank" -> {}
            "Absa Bank" -> {}
            "KCB Bank" -> {}
        }
    }

    val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    val authToken = roomDB.getUser(id = "12345678")
    private suspend fun getCryptoBuyOrder(): GetBuyOrderResponse? =
        withContext(context = Dispatchers.Main.immediate) {

            return@withContext try {
                async {
                    client.get<GetBuyOrderResponse> {
                        url("${AppConfigs.COINX_API}getCryptoOrder")
                        headers {
                            if (authToken != null) {
                                append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                            }
                        }
                        parameter(key = "orderType", value = "buyOrder")
                    }
                }.await()
            } catch (e: Exception) {
                Log.e("NetworkError", "${e.printStackTrace()}")
                null
            }

        }

    suspend fun transferredFundsNotifySeller() = withContext(context = Dispatchers.Main.immediate) {
        transferFundsState = transferFundsState.copy(
            fundsTrasfered = false,
            fundsTransferring = true,
            message = "Notifying merchant"
        )

        val getOrderData = getCryptoBuyOrder()


        try {
            async {
                client.get<TransferFundsResponse> {
                    url("${AppConfigs.COINX_API}buyerTransferredFunds")
                    headers {
                        if (authToken != null) {
                            append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                        }
                    }
                    parameter(key = "id", value = getOrderData?.message?.orderId)
                }
            }.await()
            transferFundsState = transferFundsState.copy(
                fundsTrasfered = true,
                fundsTransferring = false,
                message = "Funds transferred successfully"
            )

        } catch (e: Exception) {
            Log.e("NetworkError", "${e.printStackTrace()}")
            transferFundsState = transferFundsState.copy(
                fundsTrasfered = false,
                fundsTransferring = false,
                message = "An expected error happened"
            )
        }

    }
}