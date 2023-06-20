package online.pascarl.coinx.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.CryptoModel
import online.pascarl.coinx.model.Cryptocurrency
import java.text.NumberFormat
import java.util.Currency

class SeeAllCryptosViewModel(): ViewModel() {
    var filterSelected by mutableStateOf("Marketcap")
        private set
    var cryptocurrencies  =   mutableStateListOf<Cryptocurrency?>()
        private set
    val sortItems = listOf("Marketcap", "Price", "24h change", "7d change", "30d change")


    fun sort(sortBy:String){
        filterSelected = sortBy
        sortCryptos(sortMethod = sortBy)
    }
    fun formatCurrency(symbol:String = "KES", amount:Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }
    private fun sortCryptos(sortMethod: String) {
        val sortedList = when (sortMethod) {
            "Marketcap" -> cryptocurrencies.sortedByDescending { it?.marketCap?.toDoubleOrNull() }
            "Price" -> cryptocurrencies.sortedByDescending { it?.price?.toDoubleOrNull() }
            "24h change" -> cryptocurrencies.sortedByDescending { it?.percentageChange24h?.toDoubleOrNull() }
            "7d change" -> cryptocurrencies.sortedByDescending { it?.percentageChange7d?.toDoubleOrNull() }
            "30d change" -> cryptocurrencies.sortedByDescending { it?.percentageChange30d?.toDoubleOrNull() }
            else -> cryptocurrencies.sortedByDescending { it?.marketCap?.toDoubleOrNull() }
        }

        cryptocurrencies.clear()
        cryptocurrencies.addAll(sortedList)
    }

    private suspend fun getCryptoPrices(){
        val client = KtorClient.httpClient
        val request = client.get<String>("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"){
            header("X-CMC_PRO_API_KEY", "0f42271f-74af-4b7d-b946-fa9933b9a1f6")
            //contentType(ContentType.Application.Json)
            url {
                parameter("start", "1")
                parameter("limit","100")
                parameter("convert","KES")
            }
        }
        val jsonResponseString:String = request
        var cryptoData:List<*>
        val jsonResponseObj = Json.parseToJsonElement(jsonResponseString) as JsonObject
        cryptoData = jsonResponseObj["data"] as List<*>

        val cryptoList = mutableListOf<Cryptocurrency>()
        for (data in cryptoData){
            val dataObj = data as Map<*,*>
            val id = dataObj["id"]
            val name = dataObj["name"]
            val symbol = dataObj["symbol"]
            val priceQuote = dataObj["quote"]
            val currencyObj = Json.parseToJsonElement(priceQuote.toString()) as JsonObject
            val currency = currencyObj["KES"] as Map<String,Double>
            cryptoList.add(

                Cryptocurrency(
                    id = id.toString().toInt(),
                    name = name.toString().replace("\"", ""),
                    symbol = symbol.toString().replace("\"", ""),
                    price = currency["price"].toString(),
                    volume24h =currency["volume_24h"].toString(),
                    volumeChange24h = currency["volume_change_24h"].toString(),
                    percentageChange1h = currency["percent_change_1h"].toString(),
                    percentageChange24h =  (Math.round((currency["percent_change_24h"].toString()).toDouble()) * 100/ 1000.0).toString(),
                    percentageChange7d = currency["percent_change_7d"].toString(),
                    percentageChange30d = currency["percent_change_30d"].toString(),
                    percentageChange60d = currency["percent_change_60d"].toString(),
                    percentageChange90d = currency["percent_change_90d"].toString(),
                    marketCap = currency["market_cap"].toString() ,
                    fullyDilutedMarketCap = currency["fully_diluted_market_cap"].toString()
                )
            )
        }
        cryptocurrencies.addAll(cryptoList)


    }

    init {
        viewModelScope.launch {
            getCryptoPrices()
        }
    }
}