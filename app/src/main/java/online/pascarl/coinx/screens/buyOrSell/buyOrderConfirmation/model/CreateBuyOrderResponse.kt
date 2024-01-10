package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateBuyOrderResponse(
    val status:Boolean,
    val message:String
)