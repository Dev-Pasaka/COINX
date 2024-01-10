package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model

import kotlinx.serialization.Serializable
import online.pascarl.coinx.model.BuyOrder
import online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.dto.BuyOrderDto

@Serializable
data class GetBuyOrderResponse(
    val status:Boolean,
    val message:BuyOrderDto
)
