package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class SellAd(
    val id:String,
    val merchantUsername:String,
    val email:String,
    val cryptoName:String,
    val cryptoSymbol:String,
    val totalAmount:Double,
    val margin:Double,
    val minLimit:Double,
    val maxLimit:Double,
    val adStatus: AdStatus = AdStatus.OPEN,
    val createdAt:String
)