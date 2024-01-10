package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.dto

import kotlinx.serialization.Serializable
import online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model.OrderStatus

@Serializable
data class BuyOrderDto(
    val orderId: String,
    val adId: String,
    val buyersEmail: String,
    val cryptoName: String,
    val cryptoSymbol: String,
    val cryptoAmount: Double,
    val amountInKes: Double,
    val orderStatus: OrderStatusDto,
    val createdAt: String,
    val expiresAt: Long

)