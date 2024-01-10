package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MpesaPaybill(
    var businessNumber:String = "",
    var accountNumber:String = "",
) : Parcelable
