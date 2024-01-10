package online.pascarl.coinx.model

import online.pascarl.coinx.model.become_merchant.PaymentMethod

data class BuyAppAd(
    val username:String = "KriptoKe",
    val cryptoAmount:Double = 497.9,
    val cryptoName:String = "Bitcoin",
    var cryptoSymbol:String = "USDT",
    val minOrder:Double = 150.24,
    val maxOrder:Double = 5600.03,
    val totalOrders:Int = 957,
    val ordersCompleted:Int = 89,
    val cryptoPrice:Double = 0.0,
    val margin:Double = 0.0,
    val paymentMethod: PaymentMethod
)
