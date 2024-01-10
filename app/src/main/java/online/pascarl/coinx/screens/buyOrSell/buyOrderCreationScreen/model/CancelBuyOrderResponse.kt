package online.pascarl.coinx.screens.buyOrSell.buyOrderCreationScreen.model

import kotlinx.serialization.Serializable

@Serializable
data class CancelBuyOrderResponse(

    val status: Boolean,
    val message: String

)
