package online.pascarl.coinx.model.become_merchant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import online.pasaka.model.user.payment_platforms.MpesaPaybill
import online.pasaka.model.user.payment_platforms.MpesaSafaricom
import online.pasaka.model.user.payment_platforms.MpesaTill

@Serializable
data class PaymentMethod(
    @SerialName("mpesaSafaricom")
    val mpesaSafaricom: MpesaSafaricom? = null,

    @SerialName("mpesaPaybill")
    val mpesaPaybill: MpesaPaybill? = null,

    @SerialName("mpesaTill")
    val mpesaTill: MpesaTill? = null
)