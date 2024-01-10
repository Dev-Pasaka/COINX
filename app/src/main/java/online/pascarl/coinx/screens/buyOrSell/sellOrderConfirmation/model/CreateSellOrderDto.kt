package online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateSellOrderDto(
    val adId: String,
    val cryptoAmount: Double,
    val cryptoName: String,
    val cryptoSymbol: String
)