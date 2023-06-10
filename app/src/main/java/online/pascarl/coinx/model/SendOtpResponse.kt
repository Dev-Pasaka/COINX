package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class SendOtpResponse(
    val status:Boolean,
    val message:String,
    val otpCode:String? = null
    )