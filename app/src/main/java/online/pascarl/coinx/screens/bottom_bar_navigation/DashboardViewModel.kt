package online.pascarl.coinx.screens.bottom_bar_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import online.pascarl.coinx.apis.KtorClient
import online.pascarl.coinx.model.Cryptocurrency
import io.ktor.http.HttpHeaders.Authorization
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.model.UserPortfolio
import online.pascarl.coinx.roomDB.RoomUser
import java.text.NumberFormat
import java.util.Currency

class DashboardViewModel: ViewModel() {

    var roomUser by mutableStateOf(RoomUser())
    private var _cryptocurrencies  =   mutableStateListOf<Cryptocurrency?>()
    private val _cryptoModel  =   mutableStateListOf<CryptoModel>()
    val cryptoModel: List<CryptoModel> get() = _cryptoModel.take(10)
    private  var _expressCheckoutCryptoList  = mutableStateListOf<CryptoModel>()
    val expressCheckoutCryptoList:List<CryptoModel> get() = _expressCheckoutCryptoList.take(4)


    //UsersInformation
    private var _userInformation  by mutableStateOf(UserData())
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
            KtorClient.httpClient.get<UserData>{
                url("https://coinx.herokuapp.com/getUserData?email=${roomUser.email}")
                headers {
                    append(Authorization, "Bearer ${roomUser.token}")
                }
        }
        }catch (_: Exception){
            println("An exception was called")
            null
        }
        println("Here is user information $result")
        if (result != null) _userInformation = result
    }

    suspend fun getCryptoPrices() {
        val data = try {
            KtorClient.httpClient.get<List<Cryptocurrency>>{
                url("https://coinx.herokuapp.com/cryptoPrices")
                headers {
                    append(Authorization, "Bearer ${roomUser.token}")
                }
            }
        }catch (_: Exception){
            null
        }
        if (data != null) _cryptocurrencies.addAll(data)



    }

    fun cryptoPrices() {
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
                    )
                )
            }
        }
        _cryptoModel.clear()
        _cryptoModel.addAll(newData)

        _expressCheckoutCryptoList.clear()
        _expressCheckoutCryptoList.addAll(newData)
    }

    suspend fun getUserPortfolio(){
        val result = try {
            KtorClient.httpClient.get<UserPortfolio>{
                url("https://coinx.herokuapp.com/getUserPortfolio?email=${roomUser.email}")
                headers {
                    append(Authorization, "Bearer ${roomUser.token}")
                }
            }
        }catch (_: Exception){
            println("An exception was called")
            null
        }
        println("Here is user information $result")
        if (result != null) _userPortfolio = result
    }

    fun showBalance(){
        _showBalance = !_showBalance

    }
    fun isCoinOrWatchlist(toggle:String){
        when(toggle){
            "coin" -> _isCoinOrWatchlistSelected = true
            "watchlist" -> _isCoinOrWatchlistSelected = false
        }
    }
    fun filterCryptos(toggle:String){
        when(toggle){
            "market-cap" -> _filterChip = "market-cap"
            "price" -> _filterChip = "price"
            "24h-change" -> _filterChip = "24h-change"
        }
    }

    fun formatCurrency(symbol:String = "KES", amount:Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

  fun userBalance(symbol: String = "KES"):String{
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(userPortfolio.balance)
        return amount.replace(currency.symbol, "${currency.symbol} ")
    }


}