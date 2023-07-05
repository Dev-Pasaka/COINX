package online.pascarl.coinx.model

data class OrderData(
    var orderId:String = "",
    var adsType:String = "",
    var cryptoName:String = "",
    var cryptoSymbol:String = "",
    var minLimit :Double = 0.0,
    var maxLimit:Double = 0.0,
    var cryptoPrice :Double = 0.0,
    var cryptoAmount :Double = 0.0,
    var paymentMethod :String = "",
    var username:String = ""
)