package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model

import kotlinx.serialization.Serializable

@Serializable
data class GetSellAds(
    val id: String,
    val merchantUsername: String,
    val email: String,
    val cryptoName: String,
    val cryptoSymbol: String,
    val totalAmount: Double,
    val minLimit: Double,
    val maxLimit: Double,
    val adStatus: AdStatus,
    val margin: Double,
    val cryptoPrice:Double,
    val createdAt: String,
    val ordersMade: Long = 0,
    val ordersCompleted: Long = 0,
    val paymentMethod: PaymentMethod,
    val ordersCompletedByPercentage: Double = 0.0,
    val lastSeen: String
)