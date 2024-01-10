package online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val adId: String,
    val amountInKes: Double,
    val buyersEmail: String,
    val createdAt: String,
    val cryptoAmount: Double,
    val cryptoName: String,
    val cryptoSymbol: String,
    val expiresAt: Long,
    val orderId: String,
    val orderStatus: String
)