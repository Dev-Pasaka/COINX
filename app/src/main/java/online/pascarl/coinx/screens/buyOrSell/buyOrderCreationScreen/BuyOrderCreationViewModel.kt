package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen

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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model.CancelBuyOrderResponse
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model.OrderStatus
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.GetBuyOrderResponse

class BuyOrderCreationViewModel(val application: Application, val userRepository: UserRepository):ViewModel() {

    var adId by mutableStateOf("")
    val client = KtorClient.httpClient

    var isOrderCancelled by mutableStateOf(false)
        private set

    private val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    private val authToken = roomDB.getUser(id = "12345678")

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
    suspend fun cancelBuyOrder()  = withContext(context = Dispatchers.Main.immediate){
            val getBuyAdsResponse = try {
                val id = getCryptoBuyOrder()?.message?.orderId
                async{
                    client.get<CancelBuyOrderResponse> {
                        url("${AppConfigs.COINX_API}cancelBuyOrder")
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
                isOrderCancelled = getBuyAdsResponse.status
            }


    }

}