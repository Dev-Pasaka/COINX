package online.pascarl.coinx.model.become_merchant

import kotlinx.serialization.Serializable

@Serializable
data class BecomeMerchantResponse(
    val status:Boolean,
    val message:String
)
