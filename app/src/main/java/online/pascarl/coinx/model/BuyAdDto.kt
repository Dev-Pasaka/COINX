package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class BuyAdDto(
    val status:Boolean = false,
    val message:List<BuyAd>
)