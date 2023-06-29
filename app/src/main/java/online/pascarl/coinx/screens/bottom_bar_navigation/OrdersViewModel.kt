package online.pascarl.coinx.screens.bottom_bar_navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import online.pascarl.coinx.model.Order
import java.text.NumberFormat
import java.util.Currency
import kotlin.random.Random

class OrdersViewModel: ViewModel() {

    private var _orderSelected by mutableStateOf("All")
    val orderSelected get() = _orderSelected
    private var _dummyOrders = mutableStateListOf<Order>()

    var filteredList  = mutableStateListOf<Order>()
    init {
        dummyOrders()
        filterOrders(filterMethod = _orderSelected)
    }
    private fun dummyOrders(){
        val orderStatuses = listOf("Completed", "Pending", "Cancelled", "Expired")
        val cryptSymbols = listOf(
            "USDT", "BTC", "ETH","BNB","USDC",""
        )
        for (i in 1..10) {
            val order = Order(
                orderType = if (Random.nextBoolean()) "Sell" else "Buy",
                coinSymbol = cryptSymbols.random(),
                price = String.format("%.2f",Random.nextDouble(100.0, 1000.0)).toDouble(),
                amount = String.format("%.2f",Random.nextDouble(1.0, 20.0)).toDouble(),
                orderId = Random.nextLong(100000000000, 999999999999).toString(),
                orderStatus = orderStatuses.random(),
                orderValue = formatCurrency(amount = Random.nextDouble(100000.0, 10000000.0)),
                time = "03:05:23 18:58:15"
            )
            _dummyOrders.add(order)
        }
    }

    val orderItemFilterList = listOf(
        OrderItem("All"),
        OrderItem("Pending"),
        OrderItem("Completed"),
        OrderItem("Cancelled"),
        OrderItem("Expired"),
    )

    fun isOrderSelected(filterOrder: OrderItem){
        when(filterOrder.item){
              "All" -> _orderSelected = "All"
              "Pending" -> _orderSelected = "Pending"
              "Completed" -> _orderSelected = "Completed"
              "Cancelled" -> _orderSelected = "Cancelled"
              "Expired" -> _orderSelected = "Expired"
        }
        filterOrders(filterMethod = _orderSelected)
    }
    private fun filterOrders(filterMethod:String){
        when (_orderSelected) {
            "All" -> {
                filteredList.clear()
                filteredList.addAll(_dummyOrders)

            }
            filterMethod -> {
                filteredList.clear()
                filteredList.addAll(_dummyOrders.filter {
                    it.orderStatus == filterMethod
                })

            }
            filterMethod -> {
                filteredList.clear()
                filteredList.addAll(_dummyOrders.filter {
                    it.orderStatus == filterMethod
                })

            }
            filterMethod -> {
                filteredList.clear()
                filteredList.addAll(_dummyOrders.filter {
                    it.orderStatus == filterMethod
                })


            }
            filterMethod -> {
                filteredList.clear()
                filteredList.addAll(_dummyOrders.filter {
                    it.orderStatus == filterMethod
                })
            }
        }


    }

    private fun formatCurrency(symbol:String = "KES", amount:Double = 0.0): String {
        val formatter = NumberFormat.getCurrencyInstance()
        val currency = Currency.getInstance(symbol)
        formatter.currency = currency
        formatter.maximumFractionDigits = currency.defaultFractionDigits
        val amount = formatter.format(amount)
        return amount.replace(currency.symbol, "${currency.symbol} ")

    }


}