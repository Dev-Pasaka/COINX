package online.pascarl.coinx.screens.bottom_bar_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.JoinFull
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mode
import androidx.compose.material.icons.filled.ModeNight
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.ViewDay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.Cryptocurrency
import io.ktor.http.HttpHeaders.Authorization
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.CryptoSymbols
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.model.UserPortfolio
import online.pascarl.coinx.navigation.DrawerItems
import online.pascarl.coinx.roomDB.RoomUser
import java.text.NumberFormat
import java.util.Currency

class DashboardViewModel : ViewModel() {

    var roomUser by mutableStateOf(RoomUser())
    private var _cryptocurrencies = mutableStateListOf<Cryptocurrency?>()
    private val _cryptoModel = mutableStateListOf<CryptoModel>()
    val cryptoModel: List<CryptoModel> get() = _cryptoModel.take(10)
    private var _expressCheckoutCryptoList = mutableStateListOf<CryptoModel>()
    val expressCheckoutCryptoList: List<CryptoModel> get() = _expressCheckoutCryptoList.take(4)


    //UsersInformation
    private var _userInformation by mutableStateOf(UserData())
    val userInformation get() = _userInformation

    //UserPortfolio
    private var _userPortfolio by mutableStateOf(UserPortfolio())
    private val userPortfolio get() = _userPortfolio
    private var _showBalance by mutableStateOf(true)
    val showBalance get() = _showBalance

    //Cryptos and Watchlist toggle
    private var _isCoinOrWatchlistSelected by mutableStateOf(true)
    val isCoinOrWatchlistSelected get() = _isCoinOrWatchlistSelected

    // Filter chips toggle
    private var _filterChip by mutableStateOf("market-cap")
    val filterChip get() = _filterChip

    //PUll to refresh
    private var _isRefreshing by mutableStateOf(false)
    val isRefreshing get() = _isRefreshing

    fun refresh() {
        viewModelScope.launch {

            _isRefreshing = false
        }
    }

    fun sortCryptos(sortMethod: String) {
        println("Here is the token : ${roomUser.token}")
        val sortedList = when (sortMethod) {
            "market-cap" -> _cryptoModel.sortedByDescending { it.marketCap }
            "price" -> _cryptoModel.sortedByDescending { it.price }
            "24h-change" -> _cryptoModel.sortedByDescending { it.percentageChangeIn24Hrs }
            else -> _cryptoModel.sortedByDescending { it.marketCap }
        }

        _cryptoModel.clear()
        _cryptoModel.addAll(sortedList)
    }

    suspend fun getUserData() {
        val result = try {
            KtorClient.httpClient.get<UserData> {
                url("https://coinx-2590f763d976.herokuapp.com/getUserData?email=${roomUser.email}")
                headers {
                    append(Authorization, "Bearer ${roomUser.token}")
                }
            }
        } catch (e: Exception) {
            println("An exception was called ${e.printStackTrace()}")
            null
        }
        println("Here is user information $result")
        _userInformation = UserData()
        result?.let {
            _userInformation = result
        }
    }

    suspend fun getCryptoPrices() {
        val client = KtorClient.httpClient
        val request = try {
            client.get<String>("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest") {
                header("X-CMC_PRO_API_KEY", "0f42271f-74af-4b7d-b946-fa9933b9a1f6")
                //contentType(ContentType.Application.Json)
                url {
                    parameter("start", "1")
                    parameter("limit", "10")
                    //parameter("coins", "Bitcoin")
                    parameter("convert", "KES")
                }
            }
        } catch (_: Exception) {
            null
        }
        request?.let {
            val jsonResponseString: String = request
            var cryptoData: List<*>
            val jsonResponseObj = Json.parseToJsonElement(jsonResponseString) as JsonObject
            cryptoData = jsonResponseObj["data"] as List<*>

            val cryptoList = mutableListOf<Cryptocurrency>()
            for (data in cryptoData) {
                val dataObj = data as Map<*, *>
                val id = dataObj["id"]
                val name = dataObj["name"]
                val symbol = dataObj["symbol"]
                val priceQuote = dataObj["quote"]
                val currencyObj = Json.parseToJsonElement(priceQuote.toString()) as JsonObject
                val currency = currencyObj["KES"] as Map<String, Double>
                cryptoList.add(
                    Cryptocurrency(
                        id = id.toString().toInt(),
                        name = name.toString(),
                        symbol = symbol.toString(),
                        price = currency["price"].toString(),
                        volume24h = currency["volume_24h"].toString(),
                        volumeChange24h = currency["volume_change_24h"].toString(),
                        percentageChange1h = currency["percent_change_1h"].toString(),
                        percentageChange24h = (Math.round((currency["percent_change_24h"].toString()).toDouble())).toString(),
                        percentageChange7d = currency["percent_change_7d"].toString(),
                        percentageChange30d = currency["percent_change_30d"].toString(),
                        percentageChange60d = currency["percent_change_60d"].toString(),
                        percentageChange90d = currency["percent_change_90d"].toString(),
                        marketCap = currency["market_cap"].toString(),
                        fullyDilutedMarketCap = currency["fully_diluted_market_cap"].toString()
                    )
                )

            }
            _cryptocurrencies.clear()
            _cryptocurrencies.addAll(cryptoList)
        }


    }
    private fun removeDoubleQuotes(input: String): String {
        return input.removeSurrounding("\"")
    }

    private fun getCryptoLogo(input: String = CryptoSymbols.input, symbol: String = "eth"):String?{
        val map = mutableMapOf<String, String>()
        // Remove the curly braces at the beginning and end of the input string
        val trimmedInput = input.trimStart('{').trimEnd('}')
        // Split the input into individual key-value pairs using ', ' as a delimiter
        val keyValuePairs = trimmedInput.split(", ")
        // Iterate through each key-value pair and extract the key and value
        keyValuePairs.forEach {
            val (key, value) = it.split("=")
            val trimmedKey = key.trim()
            val trimmedValue = value.trim()
            map[trimmedKey] = trimmedValue
        }
        // Print the entire map
        println("Resulting Map: ${map[symbol.uppercase()]}")
        return map[symbol.uppercase()]
    }

    suspend fun cryptoPrices() {
        val newData = mutableListOf<CryptoModel>()
        for (liveData in _cryptocurrencies) {
            if (liveData != null) {
                newData.add(
                    CryptoModel(
                        name = liveData.name.replace("\"", ""),
                        symbol = liveData.symbol.replace("\"", ""),
                        price = liveData.price!!.toDouble(),
                        marketCap = liveData.marketCap!!.toDouble(),
                        percentageChangeIn24Hrs = liveData.percentageChange24h!!.toDouble(),
                        logoUrl = getCryptoLogo(symbol = liveData.symbol.replace("\"", ""),)
                    )
                )
            }
        }
        _cryptoModel.clear()
        _cryptoModel.addAll(newData)

        _expressCheckoutCryptoList.clear()
        _expressCheckoutCryptoList.addAll(newData)
    }

    suspend fun getUserPortfolio() {
        val result = try {
            KtorClient.httpClient.get<UserPortfolio> {
                url("https://coinx-2590f763d976.herokuapp.com/getUserPortfolio?email=${roomUser.email}")
                headers {
                    append(Authorization, "Bearer ${roomUser.token}")
                }
            }
        } catch (_: Exception) {
            println("An portfolio exception was called")
            null
        }
        println("Here is user portfolio $result")
        result?.let {
            _userPortfolio = result
        }
    }

    fun showBalance() {
        _showBalance = !_showBalance

    }

    fun isCoinOrWatchlist(toggle: String) {
        when (toggle) {
            "coin" -> _isCoinOrWatchlistSelected = true
            "watchlist" -> _isCoinOrWatchlistSelected = false
        }
    }

    fun filterCryptos(toggle: String) {
        when (toggle) {
            "market-cap" -> _filterChip = "market-cap"
            "price" -> _filterChip = "price"
            "24h-change" -> _filterChip = "24h-change"
        }
    }

    fun formatCurrency(symbol: String = "KES", amount: Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

    fun userBalance(symbol: String = "KES"): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(userPortfolio.balance)
        return amount.replace(currency.symbol, "${currency.symbol} ")
    }

    //Navigation Drawer
    val drawerItems by mutableStateOf(
        listOf(
            DrawerItems(icon = Icons.Default.AccountBox, title = "Account"),
            DrawerItems(icon = Icons.Default.VerifiedUser, title = "Kyc Verification"),
            DrawerItems(icon = Icons.Default.JoinFull, title = "Become Merchant"),
            DrawerItems(icon = Icons.Default.Share, title = "Invite friends"),
            DrawerItems(icon = Icons.Default.Phone, title = "Contact us"),
            DrawerItems(icon = Icons.Default.Settings, title = "Settings"),
            DrawerItems(icon = Icons.Default.Logout, title = "Logout"),
        )
    )

    var selectedDrawerItem by mutableStateOf(drawerItems[0])

    var openDialog by mutableStateOf(false)
}