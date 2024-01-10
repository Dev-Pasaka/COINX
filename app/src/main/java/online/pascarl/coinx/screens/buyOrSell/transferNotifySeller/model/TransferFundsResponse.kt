package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model

import kotlinx.serialization.Serializable

@Serializable
data class TransferFundsResponse(
    val message:String,
    val status:Boolean
)