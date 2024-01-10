package online.pascarl.coinx.screens.bottom_bar_navigation.orders.model.buy

import kotlinx.serialization.Serializable

@Serializable
data class GetCryptoBuyOrdersDto(
    val message: List<Message>,
    val status: Boolean
)