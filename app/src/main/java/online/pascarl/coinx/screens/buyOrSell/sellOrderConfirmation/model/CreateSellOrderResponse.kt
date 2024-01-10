package online.pascarl.coinx.screens.buyOrSell.sellOrderConfirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateSellOrderResponse(
    val message: String,
    val status: Boolean
)