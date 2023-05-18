package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class CryptoPrice(
    val symbol:String,
    val name: String,
    var amount: Double,
    var marketPrice: Double
)
