package online.pascarl.coinx.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePassword(
    val newPassword:String,
    val phoneNumber:String?
)
