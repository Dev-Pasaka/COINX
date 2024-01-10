package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.BuyOrSellState
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetBuyAds
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetBuyAdsDto
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetSellAds
import online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model.GetSellAdsDto

class CryptoAdsScreenViewModel(val application: Application, val userRepository: UserRepository) :
    ViewModel() {
    var isBuySelected by mutableStateOf(BuyOrSellState.BUY)
    var selectedItem by mutableStateOf("USDT")
    var isDropdownExpanded by mutableStateOf(false)
        private set
    var buyCryptoAds: GetBuyAdsDto? by mutableStateOf(null)
        private set

    var filteredBuyCryptoAds:MutableList<GetBuyAds>? by mutableStateOf(null)
        private set
    var sellCryptoAds: GetSellAdsDto? by mutableStateOf(null)
        private set
    var filteredSellCryptoAds:MutableList<GetSellAds>? by mutableStateOf(null)
        private set


    val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    val authToken = roomDB.getUser(id = "12345678")



    val listOfCryptoNamesAndSymbols = mutableStateListOf(
        SelectedCrypto(name = "Tether", symbol = "USDT"),
        SelectedCrypto(name = "Bitcoin", symbol = "BTC"),
        SelectedCrypto(name = "Ethereum", symbol = "ETH"),
        SelectedCrypto(name = "USD COIN", symbol = "USDC"),
        SelectedCrypto(name = "Bnb", symbol = "BNB"),
    )


    fun openAndCloseDropdown() {
        isDropdownExpanded = !isDropdownExpanded
    }

    fun toggleBuyOrSell(state: BuyOrSellState) {
        isBuySelected = state
    }

    fun filterCryptoBySelection() {
        when (selectedItem) {

            "USDT" -> {
                filteredBuyCryptoAds = buyCryptoAds?.message?.filter { it.cryptoSymbol == "USDT" }?.toMutableList()
                filteredSellCryptoAds = sellCryptoAds?.message?.filter { it.cryptoSymbol == "USDT" }?.toMutableList()
            }

            "BTC" -> {
                filteredBuyCryptoAds =
                    buyCryptoAds?.message?.filter { it.cryptoSymbol == "BTC" }?.toMutableList()
                filteredSellCryptoAds = sellCryptoAds?.message?.filter { it.cryptoSymbol == "BTC" }?.toMutableList()

            }

            "ETH" -> {
                filteredBuyCryptoAds =
                    buyCryptoAds?.message?.filter{ it.cryptoSymbol == "ETH" }?.toMutableList()
                filteredSellCryptoAds = sellCryptoAds?.message?.filter { it.cryptoSymbol == "ETH" }?.toMutableList()

            }

            "USDC" -> {
                filteredBuyCryptoAds =
                    buyCryptoAds?.message?.filter { it.cryptoSymbol == "USDC" }?.toMutableList()
                filteredSellCryptoAds = sellCryptoAds?.message?.filter { it.cryptoSymbol == "USDC" }?.toMutableList()


            }

            "BNB" -> {
                filteredBuyCryptoAds =
                    buyCryptoAds?.message?.filter { it.cryptoSymbol == "BNB" }?.toMutableList()
                filteredSellCryptoAds = sellCryptoAds?.message?.filter { it.cryptoSymbol == "BNB" }?.toMutableList()

            }
        }
    }

    private suspend fun getBuyAds() {
        coroutineScope {
            val client = KtorClient.httpClient
            val getBuyAdsResponse = try {
                async(Dispatchers.IO) {
                    client.get<GetBuyAdsDto> {
                        url("${AppConfigs.COINX_API}getCryptoAdsFullData")
                        headers {
                            if (authToken != null) {
                                append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                            }
                        }
                        parameter(key = "adType", value = "buyAd")
                    }
                }
            } catch (e: Exception) {
                Log.e("NetworkError", "${e.printStackTrace()}")
                null
            }
            delay(2000)
            val getBuyAdsResponseResult = getBuyAdsResponse?.await()
            if (getBuyAdsResponseResult != null) {
                buyCryptoAds = getBuyAdsResponseResult
                println(buyCryptoAds)
            } else {
                buyCryptoAds = null
            }
        }
    }

    suspend fun getSellAds(){
        coroutineScope {
            val client = KtorClient.httpClient
            val getBuyAdsResponse = try {
                async(Dispatchers.IO) {
                    client.get<GetSellAdsDto> {
                        url("${AppConfigs.COINX_API}getCryptoAdsFullData")
                        headers {
                            if (authToken != null) {
                                append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                            }
                        }
                        parameter(key = "adType", value = "sellAd")
                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
            delay(2000)
            val getSellAdsResponseResult = getBuyAdsResponse?.await()

            if (getSellAdsResponseResult != null) {
                sellCryptoAds = getSellAdsResponseResult
                println(sellCryptoAds)
            }else{
                sellCryptoAds = null
            }
        }

    }


    init {
        viewModelScope.launch {
            getBuyAds()
            filterCryptoBySelection()
        }
        viewModelScope.launch {
            getSellAds()
            filterCryptoBySelection()
        }

   }

}

var ISBUYSELECTED = "Buy"


data class SelectedCrypto(
    val name: String,
    val symbol: String,
    val price: Double = 0.0
)
