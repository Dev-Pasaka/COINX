package online.pascarl.coinx.screens.bottom_bar_navigation.orders

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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.config.AppConfigs
import online.pascarl.coinx.model.Order
import online.pascarl.coinx.roomDB.RoomViewModel
import online.pascarl.coinx.roomDB.UserRepository
import online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy.GetCryptoBuyOrdersDto
import online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy.sell.GetCryptoSellOrdersDto
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.TransferFundsResponse
import online.pascarl.coinx.token
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Currency

class OrdersViewModel(
    val application: Application,
    val userRepository: UserRepository
): ViewModel() {

    val client = KtorClient.httpClient
    private var _orderSelected by mutableStateOf("All")
    val orderSelected get() = _orderSelected
    private var originalOrders = mutableStateListOf<Order>()
    var filteredList  = mutableStateListOf<Order>()

    var isOrderLoading by mutableStateOf(false)


    val roomDB = RoomViewModel(
        application = application,
        userRepository = userRepository
    )
    val authToken = roomDB.getUser(id = "12345678")

    var isDialogBoxOpen by mutableStateOf(false)
        private set

    var selectedOrder by mutableStateOf(Order())
    var orderStatus by mutableStateOf("")

    fun openOrCloseDilogBox(){
        isDialogBoxOpen = !isDialogBoxOpen
    }
    var fundsTransferredSuccessful by mutableStateOf(false)
        private set
    var fundsTransferering by mutableStateOf(false)
        private set


    val orderItemFilterList = listOf(
        OrderItem("All"),
        OrderItem("Pending"),
        OrderItem("Completed"),
        OrderItem("Canceled"),
        OrderItem("Expired"),
    )

    suspend fun isOrderSelected(filterOrder: OrderItem){
        when(filterOrder.item){
              "All" -> {
                  _orderSelected = "All"
                  isOrderLoading = true
              }
              "Pending" -> {
                  _orderSelected = "Pending"
                  isOrderLoading = true
              }
              "Completed" -> {
                  _orderSelected = "Completed"
                  isOrderLoading = true
              }
              "Canceled" -> {
                  _orderSelected = "Canceled"
                  isOrderLoading = true
              }
              "Expired" -> {
                  _orderSelected = "Expired"
                  isOrderLoading = true
              }
        }
        filterOrders(filterMethod = _orderSelected)
    }
    private suspend  fun filterOrders(filterMethod:String) = withContext(context = Dispatchers.Main.immediate){
        when (_orderSelected) {
            "All" -> {
                isOrderLoading = true
                filteredList.clear()
                val sortedList = originalOrders.sortedByDescending { convertToDecimal(it.time) }
                filteredList.addAll(sortedList)
                isOrderLoading = false

            }
            filterMethod -> {
                isOrderLoading = true
                filteredList.clear()
                val sortedList = originalOrders.sortedByDescending { convertToDecimal(it.time) }

                filteredList.addAll(sortedList.filter {
                    it.orderStatus == filterMethod
                })
                isOrderLoading = false

            }
            filterMethod -> {
                isOrderLoading = true
                filteredList.clear()
                val sortedList = originalOrders.sortedByDescending { convertToDecimal(it.time) }
                filteredList.addAll(sortedList.filter {
                    it.orderStatus == filterMethod
                })
                isOrderLoading = false

            }
            filterMethod -> {
                isOrderLoading = true
                filteredList.clear()
                val sortedList = originalOrders.sortedByDescending { convertToDecimal(it.time) }
                filteredList.addAll(sortedList.filter {
                    it.orderStatus == filterMethod
                })
                isOrderLoading = false


            }
            filterMethod -> {
                isOrderLoading = true
                filteredList.clear()
                val sortedList = originalOrders.sortedByDescending { convertToDecimal(it.time) }
                filteredList.addAll(sortedList.filter {
                    it.orderStatus == filterMethod
                })
                isOrderLoading = false
            }

            else -> {}
        }


    }

    fun convertToDecimal(input: String): Long {
        val dateFormat = SimpleDateFormat("yy-MMdd HH:mm:ss")
        val parsedDate = dateFormat.parse(input)

        return parsedDate?.time ?: 0
    }

    private fun formatCurrency(symbol:String = "KES", amount:Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }

    private suspend fun getCryptoOrder(orderType:String = "buyOrder") = withContext(context = Dispatchers.Main.immediate){
        try {
            isOrderLoading = true
            val result = client.get<String> {
                url("${AppConfigs.COINX_API}getOrders")
                headers {
                    if (token != null) {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                parameter(key = "orderType", value = orderType)
            }
            val resultObj = Json.decodeFromString<GetCryptoBuyOrdersDto>(result)
            resultObj.message.forEach {
                originalOrders.add(
                    Order(
                        orderType = "Buy",
                        coinSymbol = it.cryptoSymbol,
                        price = it.amountInKes.toInt(),
                        amount = it.cryptoAmount,
                        orderId = it.orderId,
                        orderStatus = if (it.orderStatus == "EXPIRED") "Expired"
                        else if (it.orderStatus == "PENDING") "Pending"
                        else if (it.orderStatus == "COMPLETED") "Completed"
                        else if (it.orderStatus == "CANCELLED") "Canceled"
                        else "Pending",
                        message = if(it.orderStatus == "PENDING" && orderType == "Buy")
                            "Kindly transfer funds to merchant"
                        else ""
                        ,
                        orderMessage = if(it.orderStatus == "PENDING" && orderType == "Buy")
                            "BUYER_HAS_TRANSFERRED_FUNDS"
                        else "",
                        orderValue = it.cryptoAmount.toString(),
                        time = it.createdAt
                )
                )
            }
            val reversedOriginalList = originalOrders.reversed()
            originalOrders.clear()
            originalOrders.addAll(reversedOriginalList)
            Log.d("BuyOrders", result)
           // filteredList.addAll(originalOrders)
            isOrderLoading = false
        }catch (e:Exception){
            e.printStackTrace()
            isOrderLoading = false
        }
    }


    private suspend fun getCryptoSellOrders() = withContext(context = Dispatchers.Main.immediate){
        try {
            isOrderLoading = true
            val result = client.get<String> {
                url("${AppConfigs.COINX_API}getOrders")
                headers {
                    if (token != null) {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                parameter(key = "orderType", value = "sellOrder")
            }
            val resultObj = Json.decodeFromString<GetCryptoSellOrdersDto>(result)
            resultObj.message.forEach {
                originalOrders.add(
                    Order(
                        orderType = "Sell",
                        coinSymbol = it.cryptoSymbol,
                        price = it.amountInKes.toInt(),
                        amount = it.cryptoAmount,
                        orderId = it.orderId,
                        orderStatus = if (it.orderStatus == "EXPIRED") "Expired"
                        else if (it.orderStatus == "PENDING") "Pending"
                        else if (it.orderStatus == "COMPLETED") "Completed"
                        else if (it.orderStatus == "CANCELLED") "Canceled"
                        else "Pending"
                        ,
                        message = if(it.orderStatus == "MERCHANT_HAS_TRANSFERRED_FUNDS")
                            "Please release crypto to merchant"
                        else ""
                        ,
                        orderMessage = it.orderStatus,
                        orderValue = it.cryptoAmount.toString(),
                        time = it.createdAt
                    )
                )
            }
            val reversedOriginalList = originalOrders.reversed()
            originalOrders.clear()
            originalOrders.addAll(reversedOriginalList)
            Log.d("SellOrders", result)
            // filteredList.addAll(originalOrders)
            isOrderLoading = false
        }catch (e:Exception){
            e.printStackTrace()
            isOrderLoading = false
        }
    }
    suspend fun transferredFundsNotifySeller(orderType: String) = withContext(context = Dispatchers.Main.immediate) {

        try {
            fundsTransferering = true
            async {
                client.get<TransferFundsResponse> {
                    url("${AppConfigs.COINX_API}buyerTransferredFunds")
                    headers {
                        if (authToken != null) {
                            append(HttpHeaders.Authorization, "Bearer ${authToken.token}")
                        }
                    }
                    if (orderType == "buyOrder"){
                        parameter(key = "id", value = selectedOrder.orderId)
                    }else if(orderType == "sellOrder"){
                        parameter(key = "id", value = selectedOrder.orderId)
                    }
                }
            }.await()
            fundsTransferering = false
            fundsTransferredSuccessful = true
        } catch (e: Exception) {
            fundsTransferering = false
            Log.e("NetworkError", "${e.printStackTrace()}")

        }

    }



    init {
        viewModelScope.launch {
            getCryptoOrder()
            filterOrders(filterMethod = _orderSelected)
        }
        viewModelScope.launch {
            getCryptoSellOrders()
            filterOrders(filterMethod = _orderSelected)
        }
    }


}