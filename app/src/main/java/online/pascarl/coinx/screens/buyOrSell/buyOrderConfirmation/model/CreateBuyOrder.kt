package online.pascarl.coinx.screens.buyOrSell.buyOrderConfirmation.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateBuyOrder(
    val adId: String?,
    val cryptoAmount: Double?,
    val cryptoName: String?,
    val cryptoSymbol: String?
)