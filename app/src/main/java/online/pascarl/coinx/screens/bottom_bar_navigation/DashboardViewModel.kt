package online.pascarl.coinx.screens.bottom_bar_navigation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import online.pascarl.coinx.apis.KtorClient
import online.pascarl.coinx.model.Cryptocurrency
import io.ktor.http.HttpHeaders.Authorization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.model.UserPortfolio
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository

class DashboardViewModel: ViewModel() {

    var roomUser by mutableStateOf(RoomUser())
    private var _cryptocurrencies  =   mutableStateListOf<Cryptocurrency>()
    private val _cryptoModel  =   mutableStateListOf<CryptoModel>()
    val cryptoModel: List<CryptoModel> get() = _cryptoModel.take(10)
    private  var _expressCheckoutCryptoList  = mutableStateListOf<CryptoModel>()
    val expressCheckoutCryptoList:List<CryptoModel> get() = _expressCheckoutCryptoList.take(4)


    private var _userInformation  by mutableStateOf(UserData())
    val userInformation get() = _userInformation
    private var _userPortfolio by mutableStateOf(UserPortfolio())
    val userPortfolio get() = _userPortfolio

    fun sortCryptos(sortMethod: String) {
        val sortedList = when (sortMethod) {
            "marketcap" -> _cryptoModel.sortedByDescending { it.marketCap }
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
        }!!

           _cryptocurrencies.addAll(data)



    }

    fun cryptoPrices() {
        val newData = mutableListOf<CryptoModel>()
        for (liveData in _cryptocurrencies) {
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


}