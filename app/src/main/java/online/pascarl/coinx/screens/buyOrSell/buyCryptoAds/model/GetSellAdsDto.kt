package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model

import kotlinx.serialization.Serializable

@Serializable
data class GetSellAdsDto(
    val status:Boolean = false,
    val message:List<GetSellAds>
)