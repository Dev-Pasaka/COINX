package online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy.sell

import kotlinx.serialization.Serializable

@Serializable
data class GetCryptoSellOrdersDto(
    val message: List<Message>,
    val status: Boolean
)