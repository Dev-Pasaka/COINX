package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val orderType:String = "",
    val coinSymbol:String = "",
    val price: Int = 0,
    val amount: Double = 0.0,
    val orderId:String = "",
    val orderStatus:String = "",
    val orderValue:String = "",
    val time:String = "",
    val message:String = "",
    val orderMessage:String = ""
)


