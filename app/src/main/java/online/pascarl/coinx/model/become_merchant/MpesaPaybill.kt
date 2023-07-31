package online.pasaka.model.user.payment_platforms

import kotlinx.serialization.Serializable

@Serializable
data class MpesaPaybill(
    var bussinessNumber:String? = null,
    var accountNumber:String? = null,
    )