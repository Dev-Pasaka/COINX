package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GetBuyAdsDto(
    val status:Boolean = false,
    val message:List<GetBuyAds>
) : Parcelable