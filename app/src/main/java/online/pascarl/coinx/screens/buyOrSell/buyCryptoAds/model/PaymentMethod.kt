package online.pascarl.coinx.screens.buyOrSell.buyCryptoAds.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PaymentMethod(

    @SerialName("mpesaSafaricom")
    val mpesaSafaricom: MpesaSafaricom? = null,

    @SerialName("mpesaPaybill")
    var mpesaPaybill: MpesaPaybill? = null,

    @SerialName("mpesaTill")
    val mpesaTill: MpesaTill? = null
) : Parcelable


