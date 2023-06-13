package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPhoneResponse(
    val status:Boolean,
    val message:String
)
