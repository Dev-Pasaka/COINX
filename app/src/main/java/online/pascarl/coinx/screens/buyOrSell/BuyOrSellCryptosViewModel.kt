package online.pascarl.coinx.screens.buyOrSell

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.Ads
import kotlin.random.Random

class BuyOrSellCryptosViewModel:ViewModel() {
    var isBuySelected by mutableStateOf(ISBUYSELECTED)

    // State to keep track of the selected item
    var selectedItem by mutableStateOf("USDT")
    var cryptoBuyAmount by mutableStateOf("")
    var cryptoSellAmount by mutableStateOf("")
    val cryptoBuyAmountDouble get()  = (cryptoBuyAmount.toDoubleOrNull() ?: 0.0)
    val cryptoSellAmountDouble get()  = (cryptoSellAmount.toDoubleOrNull() ?: 0.0)


    var finalListOfAds = mutableStateListOf<Ads>()
    var adsDummyList  = mutableStateListOf<Ads>()
        private set
    var isEnterAmountScreenOpen by mutableStateOf(false)
        //private set
    var showPaymentConfirmationScreen by mutableStateOf(false)
    // State to track whether the dropdown menu is expanded or not
    var isDropdownExpanded by mutableStateOf(false)
        private set




    val listOfCryptoNamesAndSymbols = mutableStateListOf(
        SelectedCrypto(name = "Tether", symbol = "USDT"),
        SelectedCrypto(name = "Bitcoin", symbol = "BTC"),
        SelectedCrypto(name = "Ethereum", symbol = "ETH"),
        SelectedCrypto(name = "USD COIN", symbol = "USDC"),
        SelectedCrypto(name = "Bnb", symbol = "BNB"),
    )

    fun showPaymentConfirmationScreen(){
        showPaymentConfirmationScreen = !showPaymentConfirmationScreen
    }
    fun toggle(toggleOption:String){
        when(toggleOption){
            "Buy" -> {
                isBuySelected = "Buy"
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected }
                        .filter{ it.cryptoSymbol == selectedItem}
                )

            }
            "Sell" ->{
                isBuySelected = "Sell"
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected }
                        .filter{ it.cryptoSymbol == selectedItem}
                )
            }
        }
    }

    fun filterCryptoBySelection(){
        when(selectedItem){
            "USDT" -> {
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected}
                        .filter { it.cryptoSymbol == selectedItem }
                )
            }
            "BTC" -> {
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected}
                        .filter { it.cryptoSymbol == selectedItem }
                )
            }
            "ETH" -> {
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected}
                        .filter { it.cryptoSymbol == selectedItem }
                )
            }
            "USDC" -> {
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected}
                        .filter { it.cryptoSymbol == selectedItem }
                )
            }
            "BNB" -> {
                finalListOfAds.clear()
                finalListOfAds.addAll(
                    adsDummyList.filter { it.adType == isBuySelected}
                        .filter { it.cryptoSymbol == selectedItem }
                )
            }
        }
    }

    fun openAndCloseDropdown(){
        isDropdownExpanded = !isDropdownExpanded
    }
    fun openOrCloseEnterAmountScreen(){
        isEnterAmountScreenOpen = !isEnterAmountScreenOpen

    }

    private fun createDummyAdsList() {
        val adsTypeList = listOf("Buy", "Sell")
        val paymentMethodList = listOf("M-Pesa Paybill", "M-Pesa Safaricom")
        val adsList = mutableListOf<Ads>()
        repeat(100) {
            val listOfCryptoNamesAndSymbols = mutableStateListOf(
                SelectedCrypto(name = "Tether", symbol = "USDT"),
                SelectedCrypto(name = "Bitcoin", symbol = "BTC"),
                SelectedCrypto(name = "Ethereum", symbol = "ETH"),
                SelectedCrypto(name = "USD COIN", symbol = "USDC"),
                SelectedCrypto(name = "Bnb", symbol = "BNB"),
            ).random()
            val ad = Ads(
                Username = "User${(0..10000).random()}",
                adType = adsTypeList.random(),
                cryptoPrice = String.format("%.2f", Random.nextDouble(150.0,5000000.0)).toDouble(),
                cryptoAmount = String.format("%.2f", Random.nextDouble(100.0, 1000.0)).toDouble() ,
                cryptoName = listOfCryptoNamesAndSymbols.name,
                cryptoSymbol = listOfCryptoNamesAndSymbols.symbol,
                minOrder = String.format("%.0f", Random.nextDouble(100.0, 150.0)).toDouble(),
                maxOrder = String.format("%.0f", Random.nextDouble(500.0, 500000.0)).toDouble(),
                totalOrders = Random.nextInt(100, 10000),
                ordersCompleted = Random.nextInt(0, 100),
                paymentMethod = paymentMethodList.random()
            )
            adsList.add(ad)
        }
        adsDummyList.clear()
        adsDummyList.addAll(adsList.sortedByDescending { it.cryptoPrice }.reversed())
        println(adsList)
    }
    init {
        createDummyAdsList()
        toggle(toggleOption = isBuySelected)
    }
}
var ISBUYSELECTED = "Buy"


data class SelectedCrypto(
    val name:String,
    val symbol:String,
)