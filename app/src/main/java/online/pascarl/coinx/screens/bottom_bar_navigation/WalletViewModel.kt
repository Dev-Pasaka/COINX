package online.pascarl.coinx.screens.bottom_bar_navigation

import android.app.Application
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.launch
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.model.CryptoSymbols
import online.pascarl.coinx.model.UserData
import online.pascarl.coinx.model.UserPortfolio
import online.pascarl.coinx.roomDB.RoomUser
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserDatabase
import online.pascarl.coinx.roomDB.UserRepository
import java.text.NumberFormat
import java.util.Currency

class WalletViewModel(val application: Application, val userRepository: UserRepository) :
    ViewModel() {

    var userPortfolio: UserPortfolio by mutableStateOf(UserPortfolio())
        private set

    var chartData by mutableStateOf(mutableListOf<ChartData>())
        private set
    var otherAssetsTotalSum by mutableStateOf(0f)
        private set
    var showBalance by mutableStateOf(true)
    var index = 0

    val emptyBalance get() = mutableStateOf(
        if (userPortfolio.balance == 0.0){
            100f
        }else 0.0f
    )

    fun showBalance() {
        showBalance = !showBalance

    }


    private fun sortAssets() {
        chartData.sortByDescending { it.data }

        userPortfolio.assets.forEach {
            if (index != 0 || index != 1 || index != 2) {
                otherAssetsTotalSum += ((userPortfolio!!.assets[index].marketPrice / userPortfolio!!.balance) * 100).toFloat()
            } else {
                val data = (it.marketPrice / userPortfolio!!.balance) * 100
                chartData.add(ChartData(data = data.toFloat(), crypto = it.name))
            }
            index++
        }
    }

    private suspend fun getUserPortfolio() {
        val roomDB = RoomViewModel(
            application = application,
            userRepository = userRepository
        )
        val authToken = roomDB.getUser(id = "12345678")
        val result = try {
            KtorClient.httpClient.get<UserPortfolio> {
                url("${AppConfigs.COINX_API}getUserPortfolio")
                headers {
                    if (authToken != null) {
                        append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                    }
                }
            }
        } catch (e: Exception) {
            println("An exception was called ${e.printStackTrace()}")
            UserPortfolio()
        }
        println("Here is user information $result")
        userPortfolio = result
        sortAssets()
    }


    fun formatCurrency(symbol: String = "KES", amount: Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

    fun getCryptoLogo(input: String = CryptoSymbols.input, symbol: String = "eth"): String? {
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

    init {
        viewModelScope.launch {
            getUserPortfolio()
        }
    }

}