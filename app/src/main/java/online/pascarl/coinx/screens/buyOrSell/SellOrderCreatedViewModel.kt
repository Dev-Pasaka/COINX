package online.pascarl.coinx.screens.buyOrSell

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
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model.CancelBuyOrderResponse
import online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model.SellOrderStatus
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.GetBuyOrderResponse
import online.pascarl.spx.screens.showMessage

class SellOrderCreatedViewModel(
    val application: Application,
    val userRepository: UserRepository
):ViewModel() {

    val client = KtorClient.httpClient

    var isOrderCancelledSuccessfully by mutableStateOf(false)
        private set

    val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    private val authToken = roomDB.getUser(id = "12345678")

    private suspend fun getCryptoSellOrder(): GetBuyOrderResponse? =
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
                        parameter(key = "orderType", value = "sellOrder")
                    }
                }.await()
            } catch (e: Exception) {
                Log.e("NetworkError", "${e.printStackTrace()}")
                null
            }

        }
    suspend fun cancelSellOrder()  = withContext(context = Dispatchers.Main.immediate){
        val getBuyAdsResponse = try {
            val id = getCryptoSellOrder()?.message?.orderId
            async{
                client.get<CancelBuyOrderResponse> {
                    url("${AppConfigs.COINX_API}cancelSellOrder")
                    headers {
                        if (authToken != null) {
                            append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                        }
                    }
                    parameter(key = "id", value = id)
                }
            }.await()
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.printStackTrace()}")
            null
        }

        if (getBuyAdsResponse != null){
            isOrderCancelledSuccessfully = getBuyAdsResponse.status
        }


    }

}