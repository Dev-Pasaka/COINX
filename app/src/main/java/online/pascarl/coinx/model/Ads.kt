package online.pascarl.coinx.model

data class Ads(
    val Username:String = "KriptoKe",
    val adType:String = "Buy",
    val cryptoAmount:Double = 497.9,
    val cryptoName:String = "Bitcoin",
    val cryptoSymbol:String = "USDT",
    val minOrder:Double = 150.24,
    val maxOrder:Double = 5600.03,
    val totalOrders:Int = 957,
    val ordersCompleted:Int = 89,
    val cryptoPrice:Double = 150.0,
    val paymentMethod:String = "M-pesa Paybill",
)
